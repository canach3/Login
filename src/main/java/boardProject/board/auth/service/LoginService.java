//package boardProject.board.auth.service;
//
//import boardProject.board.auth.dto.LoginRequest;
//import boardProject.board.auth.dto.LoginResponse;
//import boardProject.board.auth.exception.InvalidLoginInfoException;
//import boardProject.board.auth.model.CustomUserDetails;
//import boardProject.board.member.entity.Member;
//import boardProject.board.member.exception.MemberNotFoundException;
//import boardProject.board.member.repository.MemberRepository;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class LoginService {
//
//    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public LoginResponse login(LoginRequest loginRequest, HttpServletRequest request) {
//        Member member = memberRepository.findByLoginId(loginRequest.loginId())
//                .orElseThrow(MemberNotFoundException::new);
//
//        if (!passwordEncoder.matches(loginRequest.password(), member.getPassword())) {
//            throw new InvalidLoginInfoException();
//        }
//
//        UserDetails userDetails = new CustomUserDetails(member);
//        // 이미 인증이 끝났으므로 credientials 는 "" 할당
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//
//        // 빈 SecurityContext 객체를 새로 생성, 주입 후 현재 스레드의 SecurityContextHolder에 context를 등록
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(authentication);
//        SecurityContextHolder.setContext(context);
//
//            return new LoginResponse(loginRequest.loginId());
//    }
//
//    public void logout(HttpServletRequest request, HttpServletResponse response) {
//        // 세션 무효화
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.invalidate();
//        }
//
//        // (선택) JSESSIONID 쿠키 만료 처리
//        Cookie cookie = new Cookie("SESSION", null);
//        cookie.setPath("/");
//        cookie.setMaxAge(0);
//        response.addCookie(cookie);
//    }
//}