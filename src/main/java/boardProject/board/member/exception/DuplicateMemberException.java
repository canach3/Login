package boardProject.board.member.exception;

import boardProject.board.common.code.MemberErrorCode;

public class DuplicateMemberException extends MemberException{
    public DuplicateMemberException() {
        super(MemberErrorCode.DUPLICATE_MEMBER);
    }
}
