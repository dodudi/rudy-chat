package kr.it.rudy.chat.infrastructure.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StompInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null) {
            StompCommand command = accessor.getCommand();

            if (StompCommand.CONNECT.equals(command)) {
                String sessionId = accessor.getSessionId();
                log.info("STOMP CONNECT: sessionId={}", sessionId);
            } else if (StompCommand.DISCONNECT.equals(command)) {
                String sessionId = accessor.getSessionId();
                log.info("STOMP DISCONNECT: sessionId={}", sessionId);
            } else if (StompCommand.SUBSCRIBE.equals(command)) {
                String sessionId = accessor.getSessionId();
                String destination = accessor.getDestination();
                log.info("STOMP SUBSCRIBE: sessionId={}, destination={}", sessionId, destination);
            } else if (StompCommand.SEND.equals(command)) {
                String sessionId = accessor.getSessionId();
                String destination = accessor.getDestination();
                log.debug("STOMP SEND: sessionId={}, destination={}", sessionId, destination);
            }
        }

        return message;
    }
}
