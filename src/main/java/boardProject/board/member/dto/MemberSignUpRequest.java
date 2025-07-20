package boardProject.board.member.dto;

import lombok.Data;

@Data
public class MemberSignUpRequest {
    private String loginId;
    private String password;
    private String name;
}