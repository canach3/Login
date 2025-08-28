package LoginProject.login.auth.security;

import LoginProject.login.common.code.AuthErrorCode;
import LoginProject.login.common.code.ResponseCode;
import LoginProject.login.common.response.ApiResponse;
import LoginProject.login.common.response.ApiResponseWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ApiResponseWriter responseWriter;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ResponseCode code = AuthErrorCode.UNAUTHORIZED_ACCESS;
        responseWriter.write(response, code.getStatusValue(), ApiResponse.fail(code));
    }
}