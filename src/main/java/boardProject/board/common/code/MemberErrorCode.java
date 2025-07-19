package boardProject.board.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ResponseCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    DUPLICATE_MEMBER(HttpStatus.CONFLICT, "이미 존재하는 사용자입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
