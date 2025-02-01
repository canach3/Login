//package boardProject.board.config.filter;
//
//import boardProject.board.auth.entity.Session;
//import boardProject.board.auth.repository.SessionRepository;
//import jakarta.servlet.*;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.Optional;
//
////@Component
//@RequiredArgsConstructor
//@Slf4j
//public class LoginCheckFilter implements Filter {
//
//    private final SessionRepository sessionRepository;
//
//    private static final String[] whiteList = {"/", "/member/save", "member/login"}; // 더 추가하기
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        String requestURI = httpRequest.getRequestURI();
//
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        log.info("인증 체크 필터 시작{}", requestURI);
//        Cookie[] cookies = httpRequest.getCookies();
//
//        Long memberId = null;
//
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                String key = cookie.getName();
//
//                if (key.equals("session")) {
//                    Optional<Session> sessionOptional = sessionRepository.findBySessionId(cookie.getValue());
//                    if (sessionOptional.isPresent() && sessionOptional.get().getExpiredDateTime().isAfter(LocalDateTime.now())) {
//                        memberId = sessionOptional.get().getMemberId();
//                    }
//                }
//            }
//        }
//
//        if (memberId != null) {
//            httpRequest.setAttribute("memberId", memberId);
//        }
//
//        chain.doFilter(request, response);
//    }
//}
