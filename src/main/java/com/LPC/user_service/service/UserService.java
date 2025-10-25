package com.LPC.user_service.service;

import com.LPC.user_service.exception.DuplicateEmailException;
import com.LPC.user_service.model.User;
import com.LPC.user_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor   // generates constructor for final fields
public class UserService {

    private final UserRepository userRepository;

    @GetMapping
    public List<User> getUsers()
    {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with id: " + id));
    }

    public void addNewUser(User user) {
        log.debug("Attempting to add user to DB with email={}", user.getEmail());
        try{
            userRepository.save(user);
        } catch(DataIntegrityViolationException e) {
            log.error("Data integrity violation while trying to add new user with email={}" + user.getEmail(), e.getMessage(),e);
            throw new DuplicateEmailException("Email already in use: " + user.getEmail());
        } catch(Exception e) {
            log.error("Unexpected error while trying to add new user" + e.getMessage(),e);
            throw e;
        }
    }

    public void deleteUser(Long userId)
    {
        if(!userRepository.existsById(userId))
        {
            log.error("User with id: " + userId + " does not exist");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    @Transactional
    public User updateUser(Long userId, User user) {
        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No user found with id: " + userId));

        applyUserUpdate(userToUpdate, user);

        return userRepository.save(userToUpdate);
    }

    public void applyUserUpdate(User target, User source)
    {
        target.setEmail(source.getEmail());
        target.setName(source.getName());
        target.setDateOfBirth(source.getDateOfBirth());
    }
}
