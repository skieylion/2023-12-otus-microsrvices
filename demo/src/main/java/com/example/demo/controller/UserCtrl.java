package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "User endpoints", description = "User operations")
public class UserCtrl {
    private final UserService userService;

    @PostMapping("/users")
    @Operation(summary = "Create a new user")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/users")
    @Operation(summary = "Get all users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{login}")
    @Operation(summary = "Find a user by a login")
    public Optional<User> findUserByLogin(@PathVariable("login") String login) {
        return userService.findUserByLogin(login);
    }

    @PutMapping("/users/{login}")
    @Operation(summary = "Update an user")
    public User updateUser(@PathVariable("login") String login, @RequestBody User user) {
        return userService.updateUser(login, user);
    }

    @DeleteMapping("/users/{login}")
    @Operation(summary = "Remove an user")
    public void deleteUser(@PathVariable("login") String login) {
        userService.deleteUser(login);
    }

}
