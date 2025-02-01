package boardProject.board.bookmark.entity;

import boardProject.board.member.entity.Member;
import boardProject.board.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Bookmark {

    @Id @Column(name = "bookmark_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
