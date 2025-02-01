package boardProject.board.auth.controller;

import boardProject.board.auth.dto.MemberLoginReq;
import boardProject.board.auth.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm() {
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberLoginReq loginReq,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletResponse response) {

        loginService.login(loginReq, response);
        System.out.println("리다이렉트!!!!!!!!!!! " + redirectURL);

        return "redirect:" + redirectURL;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        loginService.logout(request);

        return "redirect:/";
    }

    // 특정 페이지에서 로그인 여부 확인하기
    // HttpSession으로 구현해보기
    // -> 세션에 해당하는 멤버 value를 가져와서 그게 있으면 해당 유저 맞춤 페이지, NULL이면 로그인 화면 표시
}
