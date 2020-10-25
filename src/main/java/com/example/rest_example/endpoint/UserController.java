package com.example.rest_example.endpoint;

import com.example.rest_example.dto.AuthRequest;
import com.example.rest_example.dto.AuthResponse;
import com.example.rest_example.dto.UserDto;
import com.example.rest_example.exception.DuplicateEntityException;
import com.example.rest_example.exception.ResourceNotFoundException;
import com.example.rest_example.exception.UserNotFoundException;
import com.example.rest_example.model.User;
import com.example.rest_example.repository.UserRepository;
import com.example.rest_example.service.UserService;
import com.example.rest_example.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final JwtTokenUtil tokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/users")
    public List<User> users() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public User getById(@PathVariable("id") int id) {
        return userService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User dose not exists"));
    }
    @PutMapping("/user/{id}")
    public User update(@RequestBody User user, @PathVariable("id") int id) {
        User userFromDB = userService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User dose not exists"));
        userFromDB.setName(user.getName());
        userFromDB.setSurname(user.getSurname());
        userFromDB.setEmail(user.getEmail());
        userFromDB.setPassword(user.getPassword());
        userFromDB.setUserType(user.getUserType());
        return userService.save(user);
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable("id") int id) {
        userService.delete(id);
    }

    @PostMapping("/user/auth")
    public ResponseEntity auth(@RequestBody AuthRequest authRequest) {
        Optional<User> byEmail = userService.findByEmail(authRequest.getEmail());

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
    public User save(@RequestBody UserDto userDto) {
        if (userService.findOneByEmail(userDto.getEmail()).isEmpty()) {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            User user = modelMapper.map(userDto, User.class);
            return userService.save(user);
        } else {
            throw new DuplicateEntityException("username already exists!");
        }
    }

    @PutMapping("/user/{userId}/image")
    public ResponseEntity uploadImage(@PathVariable("userId") int userId, @RequestParam(value = "image", required = false) MultipartFile file) {
        try {
            User byId = userService.findOneById(userId);
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File uploadFile = new File("D:\\RestExample\\upload", fileName);
            file.transferTo(uploadFile);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }
}
