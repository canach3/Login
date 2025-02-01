package boardProject.board.auth.service;

import boardProject.board.auth.entity.Session;
import boardProject.board.auth.repository.SessionRepository;
import boardProject.board.member.entity.Member;
import boardProject.board.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionManager {
    private final SessionRepository sessionRepository;
    private final MemberRepository memberRepository;
    private static final long SESSION_EXPIRED_HOURS = 3;
    public static final String SESSION_COOKIE_NAME = "sessionId";

    // 세션 생성
    public Session createSession(Member member) {
        Session session = new Session();
        session.setSessionId(UUID.randomUUID().toString());
        session.setMemberId(member.getId());
        session.setExpiredDateTime(LocalDateTime.now().plusHours(SESSION_EXPIRED_HOURS));
        sessionRepository.save(session);

        return session;
    }

    // 세션 조회
    public Session getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie == null) {
            return null;
        }
        Optional<Session> sessionOpt = sessionRepository.findBySessionId(sessionCookie.getValue());

        // 세션이 존재할 경우 반환, 없으면 null 반환
        return sessionOpt.orElse(null);
    }

    public Member getMember(Session session) {
        long memberId = session.getMemberId();

        return memberRepository.findById(memberId).orElse(null);
    }

    // 세션 파기
    public void expire(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie != null) {
            sessionRepository.deleteBySessionId(sessionCookie.getValue());
        }
    }

    public Cookie findCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) {
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }
}
