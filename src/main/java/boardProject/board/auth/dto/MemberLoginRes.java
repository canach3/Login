package boardProject.board.auth.dto;

import boardProject.board.member.entity.Member;
import lombok.Data;

@Data
public class MemberLoginRes {

    private String loginId;
    private String password;

    public static MemberLoginRes toMemberLoginRes(Member member) {
        MemberLoginRes memberLoginRes = new MemberLoginRes();
        memberLoginRes.setLoginId(member.getLoginId());

        return memberLoginRes;
    }
}
