package com.example.backup.study.controller;

import com.example.backup.study.domain.entity.Member;
import com.example.backup.study.dto.MemberDto;
import com.example.backup.study.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class MemberController{
    // 스프링이 관리를 하면 스프링 에서 받아서 써야한다.
    // 객체를 new 해서 여러개를 쓸필요가 없다 하나만 만드는게 좋다.
    // private final MemberService memberService = new MemberService();

    private final MemberService memberService;

    @Autowired // 생성자 주입
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
        System.out.println("memberService = " + memberService.getClass());
    }

    // 메인페이지
    @GetMapping("/")
    public String index(){
        return "index";
    }

    // 회원가입 페이지
    @GetMapping("/members/register")
    public String singUp(){
        return "register";
    }

    // 회원가입 처리
    @PostMapping("members/register")
    public String singUp(MemberDto memberDto){
        memberService.join(memberDto);
        return "redirect:/members/login";
    }

    // 로그인 페이지
    @GetMapping("/members/login")
    public String login(){
        return "login";
    }
    // 로그인 성공 페이지
    @PostMapping("/members/login")
    public String success(){
        return "redirect:/";
    }
    @GetMapping("/members/denied")
    public String denied(){
        return "404";
    }

}
