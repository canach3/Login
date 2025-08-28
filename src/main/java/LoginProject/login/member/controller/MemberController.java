package LoginProject.login.member.controller;

import LoginProject.login.auth.annotation.CurrentUser;
import LoginProject.login.auth.model.LoginMember;
import LoginProject.login.common.response.ApiResponse;
import LoginProject.login.member.dto.MemberInfoResponse;
import LoginProject.login.member.dto.MemberSignUpRequest;
import LoginProject.login.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static LoginProject.login.common.code.SuccessCode.*;

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

//    // setAttribute, getAttribute 방법
//    @GetMapping("/me")
//    public ResponseEntity<ApiResponse<MemberInfoResponse>> getMyInfo(HttpServletRequest request) {
//        LoginMember loginMember = (LoginMember) request.getAttribute("loginMember");
//        MemberInfoResponse memberInfoResponse = memberService.getMyInfo(loginMember.id());
//
//        return ResponseEntity.ok(ApiResponse.success(OK, memberInfoResponse));
//    }

    // @CurrentUser 커스텀 애노테이션 방법
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MemberInfoResponse>> getMyInfo(@CurrentUser LoginMember loginMember) {
        MemberInfoResponse memberInfoResponse = memberService.getMyInfo(loginMember.id());

        return ResponseEntity.ok(ApiResponse.success(OK,memberInfoResponse));
    }
}