package com.lpc.user.service;
import com.lpc.user.exception.DuplicateEmailException;
import com.lpc.user.kafka.producer.KafkaProducer;
import com.lpc.user.model.User;
import com.lpc.user.repository.UserRepository;
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


    //@Value("${spring.kafka.topic.user-events:user-events}")
    //private String userTopic;
    private final KafkaProducer kafkaProducer;

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
            User savedUser = userRepository.save(user);
            //kafkaProducer.sendMessage("user-events", savedUser.toString());
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
        target.setFilePath(source.getFilePath());
    }

    @Transactional
    public User updateUserFilePath(Long userId, String filePath)
    {
        log.debug("Attempting to update filePath with id={}", userId + "from event");
        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No user found with id: " + userId));
        userToUpdate.setFilePath(filePath);
        log.debug("filePath updated in user with id={}", userId);
        return userRepository.save(userToUpdate);
    }
}
