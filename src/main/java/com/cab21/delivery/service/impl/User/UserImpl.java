package com.cab21.delivery.service.impl.User;

import com.cab21.delivery.dto.request.User.UpdateUserRequest;
import com.cab21.delivery.service.UserService;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.cab21.delivery.model.User;
import com.cab21.delivery.repository.UserRepository;
import com.cab21.delivery.dto.request.User.CreateUserRequest;

import jakarta.transaction.Transactional;

@Service
@EnableWebMvc
@EnableAsync
public class UserImpl implements UserService {
    private final UserRepository users;
    private final PasswordEncoder encoder;


    public UserImpl(UserRepository users, PasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    @Transactional
    public String createUser(CreateUserRequest req) {
        if (req.getUsername() == null || req.getUsername().isBlank()) {
            throw new IllegalArgumentException("username is required");
        }
        if (req.getPassword() == null || req.getPassword().isBlank()) {
            throw new IllegalArgumentException("password is required");
        }
        if (users.existsByUsername(req.getUsername())) {
            throw new IllegalArgumentException("username already exists");
        }
        if (req.getEmail() != null && !req.getEmail().isBlank() && users.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("email already exists");
        }
        User u = new User();
        u.setUsername(req.getUsername().trim());
        u.setPasswordHash(encoder.encode(req.getPassword()));
        u.setEmail(req.getEmail());
        u.setFirstName(req.getFirstName());
        u.setLastName(req.getLastName());
        u.setBirthday(req.getBirthday());
        u.setRegistryNumber(req.getRegistryNumber());
        u.setStatus(1);
        if(req.getRole() == null){
            u.setRole("user");
        } else {
            u.setRole(req.getRole());
        }

        users.save(u);
        return "success";
    }

    @Transactional
    public String updateUser(UpdateUserRequest req) {
        if (req.getId() == null) {
            throw new IllegalArgumentException("id is required");
        }

        User u = users.findById(req.getId())
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
        // username
        if (req.getUsername() != null) {
            String newUsername = req.getUsername().trim();
            if (newUsername.isBlank()) {
                throw new IllegalArgumentException("username cannot be blank");
            }
            if (users.existsByUsernameAndIdNot(newUsername, u.getId())) {
                throw new IllegalArgumentException("username already exists");
            }
            u.setUsername(newUsername);
        }

        if (req.getPassword() != null) {
            String pw = req.getPassword().trim();
            if (pw.isBlank()) {
                throw new IllegalArgumentException("password cannot be blank");
            }
            u.setPasswordHash(encoder.encode(pw));
        }

        // email (allow clearing with blank if you want; or ignore blank)
        if (req.getEmail() != null) {
            String email = req.getEmail().trim();
            if (!email.isBlank()) {
                if (users.existsByEmailAndIdNot(email, u.getId())) {
                    u.setEmail(email);
                } else if (users.existsByEmail(email)) {
                    throw new IllegalArgumentException("и-мэйл аль хэдийн бүртгэгдсэн байна");
                }
             
            } else {
                u.setEmail(null);
            }
        }
        u.setFirstName(req.getFirstName());
        u.setLastName(req.getLastName());
        u.setBirthday(req.getBirthday());
        u.setRegistryNumber(req.getRegistryNumber());
        users.save(u);
        return "success";
    }

    @Override
    @Transactional
    public String deactivateUser(Long id) {
        if (id == null) throw new IllegalArgumentException("id is required");

        User u = users.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));

        if (u.getStatus() == 0) return "already inactive";

        u.setStatus(0);
        users.save(u);
        return "success";
    }

    @Transactional
    public String reactivateUser(Long id) {
        User u = users.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));

        if (u.getStatus() == 1) return "already active";

        if (users.existsByUsernameAndStatus(u.getUsername(), 1))
            throw new IllegalArgumentException("username already taken by an active user");
        if (u.getEmail() != null && users.existsByEmailAndStatus(u.getEmail(), 1))
            throw new IllegalArgumentException("email already taken by an active user");

        u.setStatus(1);
        users.save(u);
        return "success";
    }
}
