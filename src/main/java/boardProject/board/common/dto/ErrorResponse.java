package boardProject.board.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String code;    // 예: "ACCESS_TOKEN_INVALID"
    private String message; // 예: "유효하지 않은 액세스 토큰입니다."
}