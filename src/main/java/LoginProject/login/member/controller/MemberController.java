package LoginProject.login.member.controller;

import LoginProject.login.auth.model.CustomUserDetails;
import LoginProject.login.common.response.ApiResponse;
import LoginProject.login.member.dto.MemberInfoResponse;
import LoginProject.login.member.dto.MemberSignUpRequest;
import LoginProject.login.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static LoginProject.login.common.code.SuccessCode.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ApiResponse<Void> signUp(@RequestBody MemberSignUpRequest memberSignUpRequest) {
        memberService.save(memberSignUpRequest);
        return ApiResponse.success(NO_CONTENT);
    }

    @GetMapping("/me")
    public ApiResponse<MemberInfoResponse> getMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        MemberInfoResponse memberInfoResponse = memberService.getMyInfo(userDetails.getId());
        return ApiResponse.success(OK,memberInfoResponse);
    }
}