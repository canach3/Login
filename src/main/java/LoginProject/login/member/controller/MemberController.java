package LoginProject.login.member.controller;

import LoginProject.login.common.response.ApiResponse;
import LoginProject.login.member.dto.MemberSignUpRequest;
import LoginProject.login.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static LoginProject.login.common.code.SuccessCode.NO_CONTENT;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signUp(@RequestBody MemberSignUpRequest memberSignUpRequest) {
        memberService.save(memberSignUpRequest);
        return ResponseEntity.ok(ApiResponse.success(NO_CONTENT));
    }
}