package com.LPC.user_service.service;

import com.LPC.user_service.model.User;
import com.LPC.user_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Objects;

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
                .orElseThrow(() -> new RuntimeException("No user found with id: " + id));
    }

    public void addNewUser(User user) {
        /*
        Optional<User> userOptional =  userRepository.findUserByEmail(user.getEmail());
        if(userOptional.isPresent())
        {
            throw new IllegalStateException("email already taken");
        }*/
        if(userRepository.existsByEmail(user.getEmail()))
        {
            throw new IllegalArgumentException("User already created with this email!");
        }
        userRepository.save(user);
    }

    public void deleteUser(Long userId)
    {
        if(!userRepository.existsById(userId))
        {
            throw new IllegalStateException("User with id " + userId + " doesn't exist");
        }
        userRepository.deleteById(userId);
    }

    @Transactional
    public User updateUser(Long userId, User user) {
        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "user with id " + userId + " doesn't exist"));
        validateUserUpdate(userToUpdate, user);
        applyUserUpdate(user);
        /*validation into a diff method
        if (user.getName() != null && !user.getName().isEmpty()
                && !Objects.equals(userToUpdate.getName(), user.getName())) {
            userToUpdate.setName(user.getName());
        }

        if (user.getEmail() != null && !user.getEmail().isEmpty()
                && !Objects.equals(userToUpdate.getEmail(), user.getEmail())) {
            Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
            if (userOptional.isPresent()) {
                throw new IllegalStateException("email already taken");
            }
            userToUpdate.setEmail(user.getEmail());
        }

        if (user.getDateOfBirth() != null && !Objects.equals(userToUpdate.getDateOfBirth(), user.getDateOfBirth())) {
            userToUpdate.setAge(user.getAge());
        }
        */
        return userRepository.save(userToUpdate);
    }

    public void validateUserUpdate(User userToUpdate, User user) {
        if(user.getEmail() != null && !user.getEmail().isBlank()
            && !Objects.equals(userToUpdate.getEmail(), user.getEmail())
            && userRepository.existsByEmail(user.getEmail()))
        {
            throw new IllegalStateException("Email is already taken!");
        }
    }

    public void applyUserUpdate(User user)
    {
        if(user.getEmail() != null && !user.getEmail().isBlank())
        {
            user.setEmail(user.getEmail());
        }
        if(user.getName() != null && !user.getName().isBlank())
        {
            user.setName(user.getName());
        }
        if(user.getDateOfBirth() != null)
        {
            user.setDateOfBirth(user.getDateOfBirth());
        }
    }
}
