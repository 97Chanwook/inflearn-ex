package com.wookis.ex.login.web.login;

import com.wookis.ex.login.domain.login.LoginService;
import com.wookis.ex.login.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login/sign-in")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "/login/sign-in/loginForm";
    }

    @PostMapping("/login/sign-in")
    public String login(@Validated @ModelAttribute("loginForm") LoginForm loginForm,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/login/sign-in/loginForm";
        }

        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 일치하지 않습니다.");
            return "/login/sign-in/loginForm";
        }

        //로그인 성공 처리 TODO
        return "redirect:/login";

    }
}
