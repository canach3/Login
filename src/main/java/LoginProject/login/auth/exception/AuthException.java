package LoginProject.login.auth.exception;

import LoginProject.login.common.ApplicationException;
import LoginProject.login.common.code.ResponseCode;

public class AuthException extends ApplicationException {
    public AuthException(ResponseCode errorCode) {
        super(errorCode);
    }
}
