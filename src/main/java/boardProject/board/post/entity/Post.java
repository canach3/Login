package boardProject.board.post.entity;

import boardProject.board.comment.entity.Comment;
import boardProject.board.member.entity.Member;
import boardProject.board.post.dto.PostSaveReq;
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
public class Post {
    @Id @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String body;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDt_P;

    @Column
    private Boolean isPublic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    public static Post toPost(PostSaveReq postSaveReq, Member member) {
        Post post = new Post();
        post.setTitle(postSaveReq.getTitle());
        post.setBody(postSaveReq.getBody());
        post.setMember(member);
        post.setIsPublic(postSaveReq.isPublic());

        return post;
    }
}