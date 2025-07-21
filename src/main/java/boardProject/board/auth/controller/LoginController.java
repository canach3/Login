package boardProject.board.auth.controller;

import boardProject.board.auth.dto.LoginRequest;
import boardProject.board.auth.dto.LoginResponse;
import boardProject.board.auth.service.LoginService;
import boardProject.board.common.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static boardProject.board.common.code.SuccessCode.OK;

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
}