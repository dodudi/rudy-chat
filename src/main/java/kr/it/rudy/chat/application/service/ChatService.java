package kr.it.rudy.chat.application.service;

import kr.it.rudy.chat.application.dto.ChatCommand;
import kr.it.rudy.chat.domain.chat.ChatMessage;
import kr.it.rudy.chat.domain.chat.ChatPolicy;
import kr.it.rudy.chat.domain.chat.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository repository;

    public ChatMessage send(ChatCommand cmd) {
        ChatMessage msg = new ChatMessage(cmd.nickname(), cmd.message());
        ChatPolicy.validate(msg);
        repository.publish(cmd.roomId(), msg);
        return msg;
    }
}
