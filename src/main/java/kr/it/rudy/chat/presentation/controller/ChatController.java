package kr.it.rudy.chat.presentation.controller;

import kr.it.rudy.chat.domain.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final KafkaTemplate<String, ChatMessage> kafkaTemplate;
    private static final String TOPIC = "chat-messages";

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        log.info("Received message from {}: {}", chatMessage.getSender(), chatMessage.getMessage());

        // Kafka로 메시지 발행
        kafkaTemplate.send(TOPIC, chatMessage);
        log.debug("Message sent to Kafka topic: {}", TOPIC);
    }
}
