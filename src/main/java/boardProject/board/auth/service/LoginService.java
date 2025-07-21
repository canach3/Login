package boardProject.board.auth.service;

import boardProject.board.auth.dto.LoginRequest;
import boardProject.board.auth.dto.LoginResponse;
import boardProject.board.auth.exception.InvalidLoginInfoException;
import boardProject.board.member.entity.Member;
import boardProject.board.member.exception.MemberNotFoundException;
import boardProject.board.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    public LoginResponse login(LoginRequest loginRequest, HttpServletResponse response) {
        Member member = memberRepository.findByLoginId(loginRequest.loginId())
                .orElseThrow(MemberNotFoundException::new);

        if (!BCrypt.checkpw(loginRequest.password(), member.getPassword())) {
            throw new InvalidLoginInfoException();
        }

        return new LoginResponse(member.getLoginId());
    }
}