package boardProject.board.member.service;

import boardProject.board.member.dto.MemberSaveReq;
import boardProject.board.member.entity.Member;
import boardProject.board.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(MemberSaveReq memberSaveReq) {
        Member member = Member.toMember(memberSaveReq);
        memberRepository.save(member);
    }
}
