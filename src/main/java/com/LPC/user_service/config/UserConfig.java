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
            User dobrogi = new User(
                    "Viktator",
                    "viktator@gmail.com",
                    LocalDate.of(1970, APRIL,1)
            );
            User putinberenc = new User(
                    "Putinberenc",
                    "putinberenc@gmail.com",
                    LocalDate.of(1970, APRIL,1)
            );
            repository.saveAll(
                    List.of(dobrogi,putinberenc)
            );
        };


    }
}
