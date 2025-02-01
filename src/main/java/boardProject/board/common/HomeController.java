package boardProject.board.common;

import boardProject.board.auth.entity.Session;
import boardProject.board.auth.repository.SessionRepository;
import boardProject.board.auth.service.SessionManager;
import boardProject.board.member.entity.Member;
import boardProject.board.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final SessionManager sessionManager;

//    @GetMapping("/")
//    public String home() {
//        return "home";
//    }

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {

        Session session = sessionManager.getSession(request);
        if (session == null) {
            System.out.println("Session is null");
            return "home";
        }

        Member member = sessionManager.getMember(session);
        if (member == null) {
            System.out.println("Member is null");
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome";
    }
}