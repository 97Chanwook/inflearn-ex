package com.wookis.ex.login;


import com.wookis.ex.login.domain.member.Member;
import com.wookis.ex.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

//    @GetMapping
    public String home() {
        return "/login/home";
    }

    @GetMapping
    public String homeLogin(@CookieValue(name="memberId", required = false) Long memberId, Model model) {
        if (memberId == null) {
            return "/login/home";
        }

        //로그인
        Member loginMember = memberRepository.findById(memberId);

        if (loginMember == null) {
            return "/login/home";
        }

        model.addAttribute("member", loginMember);
        return "/login/loginHome";
    }
}
