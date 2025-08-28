package LoginProject.login.member.service;

import LoginProject.login.member.dto.MemberInfoResponse;
import LoginProject.login.member.dto.MemberSignUpRequest;
import LoginProject.login.member.entity.Member;
import LoginProject.login.member.exception.DuplicateMemberException;
import LoginProject.login.member.exception.MemberNotFoundException;
import LoginProject.login.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(MemberSignUpRequest memberSaveRequest) {
        if (memberRepository.existsByLoginId(memberSaveRequest.loginId())) {
            throw new DuplicateMemberException();
        }

        String encodedPassword = passwordEncoder.encode(memberSaveRequest.password());
        Member member = Member.toMember(memberSaveRequest, encodedPassword);
        memberRepository.save(member);
    }

    public MemberInfoResponse getMyInfo(long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        return new MemberInfoResponse(member.getLoginId(), member.getName());
    }
}