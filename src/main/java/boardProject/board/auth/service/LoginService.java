package boardProject.board.auth.service;

import boardProject.board.auth.dto.MemberLoginReq;
import boardProject.board.member.entity.Member;
import boardProject.board.auth.entity.Session;
import boardProject.board.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;
    public static final String SESSION_COOKIE_NAME = "sessionId";

    public void login(MemberLoginReq loginReq, HttpServletResponse response) {
        // Member 조회 + loginId 검증
        Member member = memberRepository.findByLoginId(loginReq.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("아이디가 일치하는 회원이 없습니다."));

        // password 검증
        if (!member.getPassword().equals(loginReq.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        // 세션 생성, 쿠키에 추가
        Session session = sessionManager.createSession(member);
        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, session.getSessionId());
        cookie.setPath("/");  // 모든 경로에서 쿠키가 유효하도록 설정
        response.addCookie(cookie);
    }

    @Transactional
    public void logout(HttpServletRequest request) {
        sessionManager.expire(request);
    }
}
