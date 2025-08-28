package LoginProject.login.member.service;

import LoginProject.login.member.dto.MemberSignUpRequest;
import LoginProject.login.member.entity.Member;
import LoginProject.login.member.exception.DuplicateMemberException;
import LoginProject.login.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(MemberSignUpRequest memberSaveRequest) {
        if (memberRepository.existsByLoginId(memberSaveRequest.loginId())) {
            throw new DuplicateMemberException();
        }

        String encodedPassword = BCrypt.hashpw(memberSaveRequest.password(), BCrypt.gensalt());
        Member member = Member.toMember(memberSaveRequest, encodedPassword);
        memberRepository.save(member);
    }
}
