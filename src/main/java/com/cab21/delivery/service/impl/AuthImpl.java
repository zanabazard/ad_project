package com.cab21.delivery.service.impl;

import com.cab21.delivery.dto.UserDto;
import com.cab21.delivery.dto.request.User.LoginRequest;
import com.cab21.delivery.dto.response.LoginResponse;
import com.cab21.delivery.model.User;
import com.cab21.delivery.security.JwtService;
import com.cab21.delivery.service.AuthService;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import com.cab21.delivery.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Map;
@Service
@EnableWebMvc
@EnableAsync
public class AuthImpl implements AuthService {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserRepository users;

    public AuthImpl(AuthenticationManager authManager, JwtService jwtService, UserRepository users) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.users = users;
    }

    @Override
    public LoginResponse login(LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

        User u = users.findByEmailAndStatus(req.getEmail(), 1) // status=1 active
                .orElseThrow(() -> new UsernameNotFoundException("user not found or inactive"));

        String token = jwtService.generateToken(
                u.getEmail(),
                Map.of("role", u.getRole(), "uid", u.getId())
        );

        UserDto dto = toDto(u);

        return LoginResponse.builder()
                .token(token)
                .type("Bearer")
                .user(dto)
                .build();
    }

    private static UserDto toDto(User u) {
        return UserDto.builder()
                .id(u.getId())
                .username(u.getUsername())
                .firstName(u.getFirstName())
                .lastName(u.getLastName())
                .email(u.getEmail())
                .role(u.getRole())
                .birthday(u.getBirthday())
                .registryNumber(u.getRegistryNumber())
                .build();
    }


}
