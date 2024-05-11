package com.example.auth.controller;

import com.example.auth.domain.User;
import com.example.auth.domain.UserEditDto;
import com.example.auth.domain.UserRegistrationDto;
import com.example.auth.exception.UserExistsException;
import com.example.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        try {
            userService.registerUser(userRegistrationDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(true);
        } catch (UserExistsException e) {
            return ResponseEntity.badRequest().body("The user is already registered");
        }
    }

    @GetMapping("/{username}")
    @PreAuthorize("#username==#userDetails.getUsername()")
    public ResponseEntity<Optional<User>> findUser(
            @PathVariable("username") String username,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUser(username));
    }

    @GetMapping
    @PreAuthorize("'admin'==#userDetails.getUsername()")
    public ResponseEntity<List<User>> findUsers(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUsers());
    }

    @PutMapping("/{username}")
    @PreAuthorize("#username==#userDetails.getUsername()")
    public ResponseEntity<User> editUser(
            @PathVariable("username") String username,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserEditDto userEditDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.editUser(username, userEditDto));
    }

    @DeleteMapping
    @PreAuthorize("'admin'==#userDetails.getUsername()")
    public ResponseEntity<?> deleteUsers(@AuthenticationPrincipal UserDetails userDetails) {
        userService.deleteAll();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
