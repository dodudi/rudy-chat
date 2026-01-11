package kr.it.rudy.chat.infrastructure.messaging;

import kr.it.rudy.chat.domain.chat.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;
import tools.jackson.databind.ObjectMapper;

@Slf4j
public class ChatMessageSerializer implements Serializer<ChatMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, ChatMessage data) {
        if (data == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            log.error("Failed to serialize ChatMessage", e);
            throw new RuntimeException("Failed to serialize ChatMessage", e);
        }
    }
}
