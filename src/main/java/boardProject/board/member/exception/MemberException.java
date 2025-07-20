package boardProject.board.member.exception;

import boardProject.board.common.ApplicationException;
import boardProject.board.common.code.ResponseCode;

public class MemberException extends ApplicationException {
    public MemberException(ResponseCode errorCode) {
        super(errorCode);
    }
}
