package com.example.rest_example.service;

import com.example.rest_example.exception.ResourceNotFoundException;
import com.example.rest_example.exception.UserNotFoundException;
import com.example.rest_example.model.User;
import com.example.rest_example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(int id) {
        userRepository.delete(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book dose nor exists")));
    }

    public User findOneById(int id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public Optional<User> findOneByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByEmail(String mail) {
        return userRepository.findByEmail(mail);
    }
}
