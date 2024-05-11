package com.example.auth.service;


import com.example.auth.domain.User;
import com.example.auth.domain.UserEditDto;
import com.example.auth.domain.UserRegistrationDto;
import com.example.auth.exception.UserExistsException;
import com.example.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(UserRegistrationDto userRegistrationDto) throws UserExistsException {
        if (!userRepository.existsById(userRegistrationDto.getUsername())) {
            User user = new User();
            user.setUsername(userRegistrationDto.getUsername());
            user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
            user.setFullName(userRegistrationDto.getFullName());
            user.setPhone(userRegistrationDto.getPhone());
            user.setEmail(userRegistrationDto.getEmail());
            user.setRoles(userRegistrationDto.getRoles());
            userRepository.save(user);
        } else {
            throw new UserExistsException();
        }
    }

    @Transactional(readOnly = true)
    public Optional<User> findUser(String username) {
        return userRepository.findById(username);
    }

    @Transactional(readOnly = true)
    public boolean existsUser(String username) {
        return userRepository.existsById(username);
    }

    @Transactional(readOnly = true)
    public List<User> findUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Transactional
    public User editUser(String username, UserEditDto userEditDto) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("The user is not found"));
        Optional.ofNullable(userEditDto.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(userEditDto.getFullName()).ifPresent(user::setFullName);
        Optional.ofNullable(userEditDto.getPhone()).ifPresent(user::setPhone);
        Optional.ofNullable(userEditDto.getRoles()).ifPresent(user::setRoles);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(), user.getPassword(), user.getRoles()))
                .orElseThrow(() -> new UsernameNotFoundException("The user is not found"));
    }

    @Transactional
    public void deleteAll() {
        userRepository.findAll().forEach(user -> {
            if (!Objects.equals(user.getUsername(), "admin")) {
                userRepository.delete(user);
            }
        });
    }
}
