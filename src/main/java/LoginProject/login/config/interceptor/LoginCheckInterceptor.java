package LoginProject.login.config.interceptor;

import LoginProject.login.auth.entity.Session;
import LoginProject.login.auth.exception.UnAuthorizedAccessException;
import LoginProject.login.auth.model.LoginMember;
import LoginProject.login.auth.service.SessionManager;
import LoginProject.login.member.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {
    private final SessionManager sessionManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Session session = sessionManager.getSession(request);
        if (session == null || session.getExpiredDateTime().isBefore(LocalDateTime.now())) {
            throw new UnAuthorizedAccessException();
        }

        Member member = sessionManager.getMember(session);
        if (member == null) {
            throw new UnAuthorizedAccessException();
        }

        LoginMember loginMember = new LoginMember(member.getId());
        request.setAttribute("loginMember", loginMember);
        return true;
    }
}