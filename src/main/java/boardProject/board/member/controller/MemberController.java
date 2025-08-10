package boardProject.board.member.controller;

import boardProject.board.auth.model.CustomUserDetails;
import boardProject.board.common.response.ApiResponse;
import boardProject.board.member.dto.MemberInfoResponse;
import boardProject.board.member.dto.MemberSignUpRequest;
import boardProject.board.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MemberInfoResponse>> getMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        MemberInfoResponse memberInfoResponse = memberService.getMyInfo(userDetails.getId());

        return ResponseEntity.ok(ApiResponse.success(OK,memberInfoResponse));
    }
}