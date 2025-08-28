package LoginProject.login.auth.service;

import LoginProject.login.auth.dto.LoginRequest;
import LoginProject.login.auth.dto.LoginResponse;
import LoginProject.login.auth.entity.Session;
import LoginProject.login.auth.exception.InvalidLoginInfoException;
import LoginProject.login.member.entity.Member;
import LoginProject.login.member.exception.MemberNotFoundException;
import LoginProject.login.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import static LoginProject.login.auth.service.SessionManager.SESSION_COOKIE_NAME;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    public LoginResponse login(LoginRequest loginRequest, HttpServletResponse response) {
        Member member = memberRepository.findByLoginId(loginRequest.loginId())
                .orElseThrow(MemberNotFoundException::new);

        if (!BCrypt.checkpw(loginRequest.password(), member.getPassword())) {
            throw new InvalidLoginInfoException();
        }

        Session session = sessionManager.createSession(member);
        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, session.getSessionId());
        cookie.setPath("/");  // 모든 경로에서 쿠키가 유효하도록 설정
        response.addCookie(cookie);

        return new LoginResponse(member.getLoginId());
    }

    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        sessionManager.expire(request);

        // 쿠키 삭제
        Cookie expiredCookie = new Cookie(SessionManager.SESSION_COOKIE_NAME, null);
        expiredCookie.setPath("/");
        expiredCookie.setMaxAge(0); // 즉시 만료
        response.addCookie(expiredCookie);
    }
}