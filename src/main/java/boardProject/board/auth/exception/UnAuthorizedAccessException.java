package boardProject.board.auth.exception;

import boardProject.board.common.code.AuthErrorCode;

public class UnAuthorizedAccessException extends AuthException {
    public UnAuthorizedAccessException() {
        super(AuthErrorCode.UNAUTHORIZED_ACCESS);
    }
}