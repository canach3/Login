package boardProject.board.member.controller;

import boardProject.board.member.dto.MemberSaveReq;
import boardProject.board.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/save")
    public String memberSaveForm() {
        return "memberSave";
    }

    @PostMapping("/save")
    public String memberSave(@ModelAttribute MemberSaveReq memberSaveReq) {
        memberService.save(memberSaveReq);
        return "loginForm";
    }
}