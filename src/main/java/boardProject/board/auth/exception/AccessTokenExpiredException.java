package boardProject.board.auth.exception;

import boardProject.board.common.code.AuthErrorCode;

public class AccessTokenExpiredException extends AuthException{
    public AccessTokenExpiredException() {
        super(AuthErrorCode.ACCESS_TOKEN_EXPIRED);
    }
}
