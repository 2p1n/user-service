package com.LPC.user_service.controller;

import com.LPC.user_service.model.User;
import com.LPC.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping
    public List<User> getUsers()
    {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id)
    {
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<?> registerNewUser(@Valid @RequestBody User user)
    {
        userService.addNewUser(user);
        log.debug("User registered successfully with id={}", user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("User has been successfully registered!");
    }

    @DeleteMapping(path = "{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId)
    {
        userService.deleteUser(userId);
        log.debug("User deleted successfully with id={}", userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User has been successfully deleted!");
    }

    @PutMapping(path = "{userId}")
    public ResponseEntity<User> updateUser(
            @PathVariable("userId") Long userId,
            @Valid @RequestBody(required = false) User user)
            //@RequestBody(required = false) String email) cant have 2 requestbody in one method
    {
        User updatedUser = userService.updateUser(userId, user);
        log.debug("User updated successfully with id={}", updatedUser.getId());
        return ResponseEntity.ok(updatedUser); //dropper osztály, nem csak body hanem status code meg header
    }

}
