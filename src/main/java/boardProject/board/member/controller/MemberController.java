package boardProject.board.member.controller;

import boardProject.board.auth.annotation.CurrentUser;
import boardProject.board.auth.model.LoginMember;
import boardProject.board.common.response.ApiResponse;
import boardProject.board.member.dto.MemberInfoResponse;
import boardProject.board.member.dto.MemberSignUpRequest;
import boardProject.board.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static boardProject.board.common.code.SuccessCode.*;

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