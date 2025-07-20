package boardProject.board.member.exception;

import boardProject.board.common.code.MemberErrorCode;

public class MemberNotFoundException extends MemberException{
    public MemberNotFoundException() {
        super(MemberErrorCode.MEMBER_NOT_FOUND);
    }
}
