package LoginProject.login.auth.controller;

import LoginProject.login.auth.dto.LoginRequest;
import LoginProject.login.auth.dto.LoginResponse;
import LoginProject.login.auth.service.LoginService;
import LoginProject.login.common.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static LoginProject.login.common.code.SuccessCode.NO_CONTENT;
import static LoginProject.login.common.code.SuccessCode.OK;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        LoginResponse loginResponse = loginService.login(loginRequest, response);

        return ResponseEntity.ok(ApiResponse.success(OK, loginResponse));
    }
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request, HttpServletResponse response) {
        loginService.logout(request, response);

        return ResponseEntity.ok(ApiResponse.success(NO_CONTENT));
    }
}