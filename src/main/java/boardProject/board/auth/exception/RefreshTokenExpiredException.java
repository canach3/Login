package boardProject.board.auth.exception;

import boardProject.board.common.code.AuthErrorCode;

public class RefreshTokenExpiredException extends AuthException{
    public RefreshTokenExpiredException() {
        super(AuthErrorCode.REFRESH_TOKEN_EXPIRED);
    }
}
