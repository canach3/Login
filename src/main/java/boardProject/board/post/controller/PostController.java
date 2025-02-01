package boardProject.board.post.controller;

import boardProject.board.auth.entity.Session;
import boardProject.board.auth.service.SessionManager;
import boardProject.board.post.dto.PostEditReq;
import boardProject.board.post.dto.PostSaveReq;
import boardProject.board.post.entity.Post;
import boardProject.board.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final SessionManager sessionManager;
    private final PostService postService;

    @GetMapping("/save")
    public String postSaveForm() {
        return "postSave";
    }

    @PostMapping("/save")
    public String postSave(@ModelAttribute PostSaveReq postSaveReq,
                           HttpServletRequest request) {
        Session session = sessionManager.getSession(request);
        long memberId = session.getMemberId();
        postService.save(postSaveReq, memberId);

        return "redirect:/post/list";
    }

//    @GetMapping("/list")
    public String postList(Model model) {
        List<Post> postList = postService.getPostAll();
        model.addAttribute(postList);
        return "/postList";
    }

    @GetMapping("/list")
    public String postList(@RequestParam(defaultValue = "0") int page,  // 기본값: 0페이지
                           @RequestParam(defaultValue = "10") int size, // 기본값: 페이지 당 10개
                           Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage = postService.getPostPage(pageable);
        model.addAttribute("postPage", postPage);

        return "/postList";
    }

    @GetMapping("/view/{id}")
    public String postView(@PathVariable Long id,
                           HttpServletRequest request,
                           Model model) {
        Post post = postService.getPost(id);
        model.addAttribute("post", post);
        long sessionMemberId = sessionManager.getSession(request).getMemberId();

        // 게시글 작성자 확인
        if(postService.isPostOwner(post, sessionMemberId)) {
            return "myPostView";
        }

        // 게시글 공개, 비공개 여부 확인
        if (postService.isPostSecret(post)) {
            model.addAttribute("isSecret", true);
        } else {
            model.addAttribute("isSecret", false);
        }

        return "postView";
    }

    @PostMapping("/delete/{id}")
    public String postDelete(@PathVariable Long id) {
        postService.delete(id);

        return "redirect:/post/list";
    }

    @GetMapping("/edit/{id}")
    public String postEditForm(@PathVariable Long id,
                               Model model) {
        Post post = postService.getPost(id);
        model.addAttribute("post", post);
        return "postEdit";
    }

    @PostMapping("/edit/{id}")
    public String postEdit(@PathVariable Long id,
                           @ModelAttribute PostEditReq postEditReq) {
        postService.editPost(postEditReq, id);

        return "redirect:/post/view/" + id;
    }
}
