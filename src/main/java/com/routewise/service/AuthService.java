package com.routewise.service;

import com.routewise.dto.LoginRequest;
import com.routewise.dto.RegisterRequest;
import com.routewise.dto.UserDTO;
import com.routewise.entity.Role;
import com.routewise.entity.User;
import com.routewise.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDTO register(RegisterRequest req) {
        if (req.getEmail() == null || req.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (userRepository.existsByEmail(req.getEmail().trim().toLowerCase())) {
            throw new IllegalArgumentException("An account with this email already exists");
        }
        if (req.getPassword() == null || req.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }

        Role role = req.getRole() != null ? req.getRole() : Role.ROLE_PASSENGER;
        String encodedPassword = passwordEncoder.encode(req.getPassword());

        User user = new User(
                req.getName() != null ? req.getName().trim() : "Passenger",
                req.getEmail().trim().toLowerCase(),
                encodedPassword,
                req.getPhone() != null ? req.getPhone().trim() : "",
                role
        );

        User saved = userRepository.save(user);
        return new UserDTO(saved.getId(), saved.getName(), saved.getEmail(), saved.getPhone(), saved.getRole());
    }

    public UserDTO login(LoginRequest req) {
        if (req.getEmail() == null || req.getPassword() == null) {
            throw new IllegalArgumentException("Email and password are required");
        }

        User user = userRepository.findByEmail(req.getEmail().trim().toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getRole());
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email.toLowerCase());
    }
}
