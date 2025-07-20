package boardProject.board.auth.exception;

import boardProject.board.common.code.AuthErrorCode;
import boardProject.board.common.code.ResponseCode;

public class InvalidLoginInfoException extends AuthException {

    public InvalidLoginInfoException() {
        super(AuthErrorCode.INVALID_CREDENTIALS);
    }
}
