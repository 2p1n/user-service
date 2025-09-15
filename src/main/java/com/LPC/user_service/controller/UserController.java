package com.LPC.user_service.controller;

import com.LPC.user_service.model.User;
import com.LPC.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public void registerNewUser(@RequestBody User user)
    {
        userService.addNewUser(user);
    }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") Long userId)
    {
        userService.deleteUser(userId);
    }

    @PutMapping(path = "{userId}")
    public ResponseEntity<User> updateUser(
            @PathVariable("userId") Long userId,
            @RequestBody(required = false) User user)
            //@RequestBody(required = false) String email) cant have 2 requestbody in one method
    {
        User updatedUser = userService.updateUser(userId, user);
        return ResponseEntity.ok(updatedUser); //dropper osztály, nem csak body hanem status code meg header
    }

}
