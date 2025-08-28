package LoginProject.login.member.exception;

import LoginProject.login.common.ApplicationException;
import LoginProject.login.common.code.ResponseCode;

public class MemberException extends ApplicationException {
    public MemberException(ResponseCode errorCode) {
        super(errorCode);
    }
}
