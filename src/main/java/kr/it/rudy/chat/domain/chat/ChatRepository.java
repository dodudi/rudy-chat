package kr.it.rudy.chat.domain.chat;

import java.util.List;

public interface ChatRepository {
    void publish(String roomId, ChatMessage message);

    List<ChatMessage> findRecent(String roomId);
}
