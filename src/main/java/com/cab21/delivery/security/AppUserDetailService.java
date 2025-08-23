package com.cab21.delivery.security;

import com.cab21.delivery.model.User;
import com.cab21.delivery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserDetailService implements UserDetailsService {
    private final UserRepository users;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = users.findByUsernameAndStatus(username, 1)
                .orElseThrow(() -> new UsernameNotFoundException("user not found or inactive"));
        var auth = new SimpleGrantedAuthority("ROLE_" + u.getRole().toUpperCase());
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPasswordHash(), List.of(auth));
    }
}
