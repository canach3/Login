package LoginProject.login.auth.exception;

import LoginProject.login.common.code.AuthErrorCode;

public class UnAuthorizedAccessException extends AuthException {
    public UnAuthorizedAccessException() {
        super(AuthErrorCode.UNAUTHORIZED_ACCESS);
    }
}