package kr.it.rudy.chat.application.dto;

public record ChatCommand(
        String roomId,
        String nickname,
        String message
) {
}
