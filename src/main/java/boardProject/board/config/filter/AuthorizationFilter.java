//package boardProject.board.config.filter;
//
//import boardProject.board.auth.entity.Session;
//import boardProject.board.auth.repository.SessionRepository;
//import jakarta.servlet.*;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//@RequiredArgsConstructor
//public class AuthorizationFilter implements Filter {
//
//    private final SessionRepository sessionInfoRepository;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest servletRequest = (HttpServletRequest) request;
//
//        Cookie[] cookies = servletRequest.getCookies();
//        Long memberId = null;
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                String key = cookie.getName();
//                if (key.equals("session")) {
//                    Optional<Session> sessionInfoOptional = sessionInfoRepository.findBySessionId(cookie.getValue());
//                    if (sessionInfoOptional.isPresent() && sessionInfoOptional.get().getExpiredDateTime().isAfter(LocalDateTime.now())) {
//                        memberId = sessionInfoOptional.get().getMemberId();
//                    }
//                }
//            }
//        }
//
//        if (memberId != null) {
//            servletRequest.setAttribute("memberId", memberId);
//        }
//
//        chain.doFilter(request, response);
//    }
//}