package boardProject.board.auth.service;

import boardProject.board.auth.dto.LoginRequest;
import boardProject.board.auth.dto.LoginResponse;
import boardProject.board.auth.entity.Session;
import boardProject.board.auth.exception.InvalidLoginInfoException;
import boardProject.board.member.entity.Member;
import boardProject.board.member.exception.MemberNotFoundException;
import boardProject.board.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import static boardProject.board.auth.service.SessionManager.SESSION_COOKIE_NAME;

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

        return new LoginResponse(member.getLoginId());
    }

    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        sessionManager.expire(request, response);
    }
}