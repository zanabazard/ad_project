// com.cab21.delivery.security.JwtAuthFilter
package com.cab21.delivery.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;               // <-- keep
    private final UserDetailsService userDetailsService; // <-- keep

     @Override
    protected boolean shouldNotFilter(HttpServletRequest req) {
    String p = req.getServletPath();
    return "/api/auth/login".equals(p)
        || "/cab21/api/auth/login".equals(p)
        || "/actuator/health".equals(p)
        || "OPTIONS".equalsIgnoreCase(req.getMethod());
  }

    @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws ServletException, IOException {

    String auth = req.getHeader("Authorization");
    if (auth == null || !auth.startsWith("Bearer ")) {
      chain.doFilter(req, res);
      return;
    }

    String token = auth.substring(7);
    String username;
    try {
      username = jwtService.extractUsername(token);
    } catch (Exception e) {
      // bad/expired token: do NOT block here; let entry point handle if endpoint needs auth
      chain.doFilter(req, res);
      return;
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails user = userDetailsService.loadUserByUsername(username);
      if (jwtService.isTokenValid(token, user.getUsername())) {
        var authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
    chain.doFilter(req, res);
  }

}
