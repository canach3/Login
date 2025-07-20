package boardProject.board.auth.exception;

import boardProject.board.common.code.AuthErrorCode;

public class InvalidLoginInfoException extends AuthException {

    public InvalidLoginInfoException() {
        super(AuthErrorCode.INVALID_CREDENTIALS);
    }
}