package com.LPC.user_service.config;

import com.LPC.user_service.model.User;
import com.LPC.user_service.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository)
    {

        return args ->
        {
            User test1 = new User(
                    "DummyUser1",
                    "DummyUser1@gmail.com",
                    LocalDate.of(1970, APRIL,1)
            );
            User test2 = new User(
                    "DummyUser2",
                    "DummyUser2@gmail.com",
                    LocalDate.of(2025, APRIL,1)
            );
            repository.saveAll(
                    List.of(test1,test2)
            );
        };


    }
}
