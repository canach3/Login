package LoginProject.login.auth.exception;

import LoginProject.login.common.code.AuthErrorCode;

public class InvalidLoginInfoException extends AuthException {
    public InvalidLoginInfoException() {
        super(AuthErrorCode.INVALID_CREDENTIALS);
    }
}