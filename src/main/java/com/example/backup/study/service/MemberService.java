package com.example.backup.study.service;

import com.example.backup.study.domain.entity.Member;
import com.example.backup.study.domain.role.Role;
import com.example.backup.study.dto.MemberDto;
import com.example.backup.study.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    // 생성자 주입
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Transactional
    public Long join(MemberDto memberDto) {
        // 같은 이메일회원 중복 x
        vailDateDuplicateMember(memberDto);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(memberDto.getPassWord());
        memberDto.setPassWord(password);
        memberDto.setPassWordConfirm(password);
        return memberRepository.save(memberDto.toEntity()).getId();
    }

    private void vailDateDuplicateMember(MemberDto memberDto) {
        memberRepository.findByEmail(memberDto.getEmail()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> userEntityMapper = memberRepository.findByEmail(username);
        Member member = userEntityMapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        if(("admin@example.com").equals(username)){
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        }else{
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        return new User(member.getEmail(), member.getPassWord(), authorities);
    }
}
