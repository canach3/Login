package boardProject.board.auth.exception;

import boardProject.board.common.ApplicationException;
import boardProject.board.common.code.ResponseCode;

public class AuthException extends ApplicationException {
    public AuthException(ResponseCode errorCode) {
        super(errorCode);
    }
}
