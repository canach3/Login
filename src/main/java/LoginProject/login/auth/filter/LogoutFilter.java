package LoginProject.login.auth.filter;

import LoginProject.login.common.code.CommonErrorCode;
import LoginProject.login.common.code.SuccessCode;
import LoginProject.login.common.response.ApiResponse;
import LoginProject.login.common.response.ApiResponseWriter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LogoutFilter extends OncePerRequestFilter {

    private final SecurityContextRepository securityContextRepository;
    private final ApiResponseWriter responseWriter;

    // 기본 매핑: POST /api/auth/logout
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !(request.getRequestURI().equals("/api/auth/logout")
                && request.getMethod().equalsIgnoreCase(HttpMethod.POST.name()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (shouldNotFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 1) SecurityContext 비우기
            SecurityContextHolder.clearContext();

            // 2) 세션 무효화 (Spring Session JDBC가 세션 정리)
            HttpSession session = request.getSession(false);
            if (session != null) {
                try {
                    session.invalidate();
                } catch (IllegalStateException ignore) {
                    // 이미 무효화된 경우
                }
            }

            // 3) Spring Session 쿠키(SESSION) 제거
            expireSpringSessionCookie(request, response);

            // 4) 리포지토리에 빈 컨텍스트 저장
            SecurityContext empty = SecurityContextHolder.createEmptyContext();
            securityContextRepository.saveContext(empty, request, response);

            // 5) 응답 바디 - GlobalExceptionHandler는 필터 단계의 예외를 자동 처리하지 않으므로 직접 구현
            responseWriter.write(response, SuccessCode.OK.getStatusValue(), ApiResponse.success(SuccessCode.OK));
        } catch (Exception e) {
            ApiResponse<?> body = ApiResponse.fail(CommonErrorCode.INTERNAL_SERVER_ERROR);
            try {
                responseWriter.write(response, CommonErrorCode.INTERNAL_SERVER_ERROR.getStatusValue(), body);
            } catch (IOException ioException) {
                throw new RuntimeException("로그아웃 요청 에러 응답 작성중 문제 발생", ioException);
            }
        }

    }

    private void expireSpringSessionCookie(HttpServletRequest request, HttpServletResponse response) {
        // Spring Session 기본 쿠키 이름: "SESSION"
        Cookie cookie = new Cookie("SESSION", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setSecure(request.isSecure());
        response.addCookie(cookie);
    }
}