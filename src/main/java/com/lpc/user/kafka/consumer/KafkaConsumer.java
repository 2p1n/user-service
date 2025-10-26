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

    @KafkaListener(topics = "${spring.kafka.topics.test-events}", groupId = "${spring.kafka.groups.test-group}")
    public void listen(FilePathEventDto event){
        log.debug("Kafka event: {}", event);
        userService.updateUserFilePath(event.getId(), event.getFilePath());
    }
}
