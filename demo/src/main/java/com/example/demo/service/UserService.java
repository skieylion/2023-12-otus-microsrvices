package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User createUser(User user) {
        log.debug("user = {}", user);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserByLogin(String login) {
        log.debug("user login = {}", login);
        return userRepository.findById(login);
    }

    @Transactional
    public User updateUser(String login, User user) {
        if (userRepository.existsById(login)) {
            user.setLogin(login);
            return userRepository.save(user);
        }
        throw new EntityNotFoundException("The user couldn't found");
    }

    @Transactional
    public void deleteUser(String login) {
        userRepository.deleteById(login);
    }

}
