package com.lpc.user.kafka.consumer;

import com.lpc.upload.dto.FilePathEventDto;

import com.lpc.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final UserService userService;

    @KafkaListener(topics = "test-events", groupId = "test-group")
    public void listen(FilePathEventDto event){
        log.debug("Kafka event");
        userService.updateUserFilePath(event.getId(), event.getFilePath());
    }
}
