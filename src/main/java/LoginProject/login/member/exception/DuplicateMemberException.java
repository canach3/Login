package LoginProject.login.member.exception;

import LoginProject.login.common.code.MemberErrorCode;

public class DuplicateMemberException extends MemberException{
    public DuplicateMemberException() {
        super(MemberErrorCode.DUPLICATE_MEMBER);
    }
}
