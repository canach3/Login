package boardProject.board.post.service;

import boardProject.board.member.entity.Member;
import boardProject.board.member.repository.MemberRepository;
import boardProject.board.post.dto.PostEditReq;
import boardProject.board.post.dto.PostSaveReq;
import boardProject.board.post.entity.Post;
import boardProject.board.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public void save(PostSaveReq postSaveReq, long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));
        Post post = Post.toPost(postSaveReq, member);

        postRepository.save(post);
    }

    public List<Post> getPostAll() {
        return postRepository.findAll();
    }

    public Page<Post> getPostPage(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Post getPost(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public void editPost(PostEditReq postEditReq, Long id) {
        Post post = getPost(id);
        post.setTitle(postEditReq.getTitle());
        post.setBody(postEditReq.getBody());
        post.setIsPublic(postEditReq.getIsPublic() != null ? postEditReq.getIsPublic() : false); // 기본값 설정
        postRepository.save(post);
    }


    public boolean isPostOwner(Post post, long sessionMemberId) {
        return post.getMember().getId() == sessionMemberId;
    }

    public boolean isPostSecret(Post post) {
        return !post.getIsPublic();

    }
}
