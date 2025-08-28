package LoginProject.login.auth.filter;

import LoginProject.login.auth.dto.LoginRequest;
import LoginProject.login.common.code.AuthErrorCode;
import LoginProject.login.common.code.MemberErrorCode;
import LoginProject.login.common.code.ResponseCode;
import LoginProject.login.common.code.SuccessCode;
import LoginProject.login.common.response.ApiResponse;
import LoginProject.login.common.response.ApiResponseWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final SecurityContextRepository securityContextRepository;
    private final ApiResponseWriter responseWriter;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // json 직접 파싱해서 넣기
            LoginRequest loginRequest = objectMapper.readValue  (request.getInputStream(), LoginRequest.class);
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.loginId(), loginRequest.password(), null);

            log.info("loginId={}", loginRequest.loginId());

            /** 아이디 비밀번호를 검증
             * → UserDetailsService.loadUserByUsername() 호출 (DB 조회)
             * → PasswordEncoder.matches()로 비밀번호 검증
             * → 성공 시 인증 완료된 Authentication 반환 (Authentication 에는 CustomUserDetails가 들어있음)
             */

            // UsernamePasswordAuthenticationFilter가 보유한 AuthenticationManager 사용
            return getAuthenticationManager().authenticate(authToken);

        } catch (IOException e) {
            throw new RuntimeException("로그인 요청 파싱 중 문제 발생", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException {
        // SecurityContext에 인증 결과를 저장하여 이후 SecurityContextPersistenceFilter가 세션에 저장하도록 함
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        // 성공 시점에 SecurityContext를 명시적으로 저장하여 세션에 확정 반영
        securityContextRepository.saveContext(context, request, response);

        ApiResponse<?> body = ApiResponse.success(SuccessCode.OK);
        responseWriter.write(response, SuccessCode.OK.getStatusValue(), body);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        // 실패 유형별로 커스텀 에러코드 매핑
        ResponseCode errorCode = mapToErrorCode(failed);
        ApiResponse<?> body = ApiResponse.fail(errorCode);
        responseWriter.write(response, errorCode.getStatusValue(), body);
    }

    private ResponseCode mapToErrorCode(AuthenticationException ex) {
        if (ex instanceof InternalAuthenticationServiceException) {
            // UserDetailsService에서 사용자 못 찾음
            return MemberErrorCode.MEMBER_NOT_FOUND;
        } else if (ex instanceof BadCredentialsException) {
            // 비밀번호 불일치
            return AuthErrorCode.INVALID_CREDENTIALS;
        }
        // 기타 인증 실패
        return AuthErrorCode.UNAUTHORIZED_ACCESS;
    }
}