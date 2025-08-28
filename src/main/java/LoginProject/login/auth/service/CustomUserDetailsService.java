package LoginProject.login.auth.service;

import LoginProject.login.auth.model.CustomUserDetails;
import LoginProject.login.member.entity.Member;
import LoginProject.login.member.exception.MemberNotFoundException;
import LoginProject.login.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) {
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(MemberNotFoundException::new);

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(member.getRole()));
        return new CustomUserDetails(member.getId(), member.getLoginId(), member.getPassword(), authorities);
    }
}