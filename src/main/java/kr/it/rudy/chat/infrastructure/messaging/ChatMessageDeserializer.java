package kr.it.rudy.chat.infrastructure.messaging;

import kr.it.rudy.chat.domain.chat.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import tools.jackson.databind.ObjectMapper;

@Slf4j
public class ChatMessageDeserializer implements Deserializer<ChatMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ChatMessage deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            return objectMapper.readValue(data, ChatMessage.class);
        } catch (Exception e) {
            log.error("Failed to deserialize ChatMessage from topic: {}", topic, e);
            return null; // 에러 발생 시 null 반환 (Consumer가 계속 동작하도록)
        }
    }
}
