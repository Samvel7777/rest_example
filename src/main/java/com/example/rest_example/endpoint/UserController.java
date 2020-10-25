package com.example.rest_example.endpoint;

import com.example.rest_example.dto.AuthRequest;
import com.example.rest_example.dto.AuthResponse;
import com.example.rest_example.exception.DuplicateEntityException;
import com.example.rest_example.exception.ResourceNotFoundException;
import com.example.rest_example.model.User;
import com.example.rest_example.repository.UserRepository;
import com.example.rest_example.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final JwtTokenUtil tokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/user/auth")
    public ResponseEntity auth(@RequestBody AuthRequest authRequest) {
        Optional<User> byEmail = userRepository.findByEmail(authRequest.getEmail());

        if (byEmail.isPresent()) {
            User user = byEmail.get();
            if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                String token = tokenUtil.generateToken(user.getEmail());
                return ResponseEntity.ok(AuthResponse.builder()
                        .token(token)
                        .name(user.getName())
                        .surname(user.getSurname())
                        .build());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }

    @PostMapping("/user")
    public User save(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } else {
            throw new DuplicateEntityException("username already exists!");
        }
    }
    
}
