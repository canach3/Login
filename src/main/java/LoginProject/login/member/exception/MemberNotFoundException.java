package LoginProject.login.member.exception;

import LoginProject.login.common.code.MemberErrorCode;

public class MemberNotFoundException extends MemberException{
    public MemberNotFoundException() {
        super(MemberErrorCode.MEMBER_NOT_FOUND);
    }
}
