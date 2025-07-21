package boardProject.board.member.service;

import boardProject.board.member.dto.MemberSignUpRequest;
import boardProject.board.member.entity.Member;
import boardProject.board.member.exception.DuplicateMemberException;
import boardProject.board.member.repository.MemberRepository;
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
