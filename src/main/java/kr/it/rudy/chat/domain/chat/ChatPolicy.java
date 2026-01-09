package kr.it.rudy.chat.domain.chat;

public class ChatPolicy {
    private static final int MAX_LENGTH = 200;

    public static void validate(ChatMessage msg) {

        if (msg.getMessage().length() > MAX_LENGTH) {
            throw new IllegalArgumentException("메시지 길이 초과");
        }

        if (msg.getMessage().contains("욕설")) {
            throw new IllegalArgumentException("금칙어 포함");
        }
    }
}
