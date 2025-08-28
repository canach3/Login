package LoginProject.login.member.entity;

import LoginProject.login.member.dto.MemberSignUpRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {
    @Id @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String loginId;

    @Column
    private String password;

    @Column
    private String name;

    public static Member toMember(MemberSignUpRequest memberSaveReq, String encodedPassword) {
        Member member = new Member();
        member.setLoginId(memberSaveReq.loginId());
        member.setPassword(encodedPassword);
        member.setName(memberSaveReq.name());

        return member;
    }
}