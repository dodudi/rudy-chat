package kr.it.rudy.chat.infrastructure.messaging;

import kr.it.rudy.chat.domain.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMessageListener {

    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "chat-messages", groupId = "chat-group")
    public void listen(ChatMessage message) {
        log.info("Received from Kafka - sender: {}, message: {}",
                message.getSender(), message.getMessage());

        // Kafka에서 받은 메시지를 WebSocket으로 브로드캐스트
        messagingTemplate.convertAndSend("/topic/public", message);
        log.debug("Message broadcasted to WebSocket clients");
    }
}
