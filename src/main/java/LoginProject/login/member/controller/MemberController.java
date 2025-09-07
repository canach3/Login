package LoginProject.login.member.controller;

import LoginProject.login.common.response.ApiResponse;
import LoginProject.login.member.dto.MemberSignUpRequest;
import LoginProject.login.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static LoginProject.login.common.code.SuccessCode.NO_CONTENT;

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
}