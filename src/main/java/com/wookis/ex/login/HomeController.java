package com.wookis.ex.login;


import com.wookis.ex.login.domain.member.Member;
import com.wookis.ex.login.domain.member.MemberRepository;
import com.wookis.ex.login.web.SessionConst;
import com.wookis.ex.login.web.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    private final SessionManager sessionManager;

//    @GetMapping
    public String home() {
        return "/login/home";
    }

//    @GetMapping
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

//    @GetMapping
    public String homeLoginV2(HttpServletRequest request, Model model) {
        //세션 관리자에 저장된 회원 정보 조회
        Member member = (Member)sessionManager.getSession(request);

        if (member == null) {
            return "/login/home";
        }

        model.addAttribute("member", member);
        return "/login/loginHome";
    }

    @GetMapping
    public String homeLoginV3(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return "/login/home";
        }

        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (member == null) {
            return "/login/home";
        }

        model.addAttribute("member", member);
        return "/login/loginHome";
    }
}
