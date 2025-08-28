package LoginProject.login.auth.service;

import LoginProject.login.auth.entity.Session;
import LoginProject.login.auth.repository.SessionRepository;
import LoginProject.login.member.entity.Member;
import LoginProject.login.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionManager {
    private final SessionRepository sessionRepository;
    private final MemberRepository memberRepository;
    private static final long SESSION_EXPIRED_MINUTES = 30;
    public static final String SESSION_COOKIE_NAME = "sessionId";

    public Session createSession(Member member) {
        Session session = new Session();
        session.setSessionId(UUID.randomUUID().toString());
        session.setMemberId(member.getId());
        session.setExpiredDateTime(LocalDateTime.now().plusMinutes(SESSION_EXPIRED_MINUTES));
        sessionRepository.save(session);

        return session;
    }

    public Session getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie == null) {
            return null;
        }
        Optional<Session> session = sessionRepository.findBySessionId(sessionCookie.getValue());

        // 세션이 존재할 경우 반환, 없으면 null 반환
        return session.orElse(null);
    }

    public Member getMember(Session session) {
        long memberId = session.getMemberId();

        return memberRepository.findById(memberId).orElse(null);
    }

    public void expire(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie != null) {
            sessionRepository.deleteBySessionId(sessionCookie.getValue());
        }
    }

    @Transactional
    @Scheduled(cron = "0 */30 * * * *") // 30분마다 실행 (초 분 시 일 월 요일)
    public void removeExpiredSessions() {
        LocalDateTime now = LocalDateTime.now();
        sessionRepository.deleteByExpiredDateTimeBefore(now);
        log.info("만료된 세션 삭제 완료 at {}", now);
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