package boardProject.board.member.entity;

import boardProject.board.post.entity.Post;
import boardProject.board.member.dto.MemberSaveReq;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDt_M;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Post> session = new ArrayList<>();

    public static Member toMember(MemberSaveReq memberSaveReq) {
        Member member = new Member();
        member.setLoginId(memberSaveReq.getLoginId());
        member.setPassword(memberSaveReq.getPassword());
        member.setName(memberSaveReq.getName());

        return member;
    }
}