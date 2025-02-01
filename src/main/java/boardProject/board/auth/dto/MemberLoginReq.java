package boardProject.board.auth.dto;

import lombok.Data;

@Data
public class MemberLoginReq {

    private String loginId;
    private String password;

}
