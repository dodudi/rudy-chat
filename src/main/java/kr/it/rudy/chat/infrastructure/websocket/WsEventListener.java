package kr.it.rudy.chat.infrastructure.websocket;

import kr.it.rudy.chat.domain.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class WsEventListener {
    private final SimpMessageSendingOperations messaging;

    @EventListener
    public void connect(SessionConnectedEvent e) {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(e.getMessage());
        String sessionId = headers.getSessionId();
        String username = headers.getUser() != null
                ? headers.getUser().getName()
                : "Anonymous";

        log.info("WebSocket connected: sessionId={}, username={}", sessionId, username);

        try {
            messaging.convertAndSend("/topic/system",
                    system(username + " 님이 접속했습니다."));
        } catch (Exception ex) {
            log.error("Failed to send connect message: {}", ex.getMessage(), ex);
        }
    }

    @EventListener
    public void disconnect(SessionDisconnectEvent e) {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(e.getMessage());
        String sessionId = headers.getSessionId();
        String username = headers.getUser() != null
                ? headers.getUser().getName()
                : "Anonymous";

        log.info("WebSocket disconnected: sessionId={}, username={}", sessionId, username);

        try {
            messaging.convertAndSend("/topic/system",
                    system(username + " 님이 퇴장했습니다."));
        } catch (Exception ex) {
            log.error("Failed to send disconnect message: {}", ex.getMessage(), ex);
        }
    }

    private ChatMessage system(String msg) {
        return new ChatMessage("SYSTEM", msg);
    }
}