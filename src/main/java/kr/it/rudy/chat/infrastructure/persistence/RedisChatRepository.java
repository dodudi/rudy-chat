package kr.it.rudy.chat.infrastructure.persistence;

import kr.it.rudy.chat.domain.chat.ChatMessage;
import kr.it.rudy.chat.domain.chat.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RedisChatRepository implements ChatRepository {
    private final SimpMessageSendingOperations messaging;
    private final RedisTemplate<String, ChatMessage> redis;

    private static final int MAX = 50;

    @Override
    public void publish(String roomId, ChatMessage msg) {

        String key = "chat:" + roomId;

        // 저장
        redis.opsForList().rightPush(key, msg);

        // 최근 50개 유지
        redis.opsForList().trim(key, -MAX, -1);

        // 브로드캐스트
        messaging.convertAndSend(
                "/topic/blog/" + roomId, msg);
    }

    @Override
    public List<ChatMessage> findRecent(String roomId) {

        return redis.opsForList()
                .range("chat:" + roomId, 0, MAX);
    }
}
