package boardProject.board.auth.exception;

import boardProject.board.common.code.AuthErrorCode;

public class AccessTokenInvalidException extends AuthException{
    public AccessTokenInvalidException() {
        super(AuthErrorCode.ACCESS_TOKEN_INVALID);
    }
}
