package boardProject.board.auth.exception;

import boardProject.board.common.code.AuthErrorCode;

public class RefreshTokenInvalidException extends AuthException{
    public RefreshTokenInvalidException() {
        super(AuthErrorCode.REFRESH_TOKEN_INVALID);
    }
}
