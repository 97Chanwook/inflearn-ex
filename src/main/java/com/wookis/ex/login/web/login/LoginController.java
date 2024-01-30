package com.wookis.ex.login.web.login;

import com.wookis.ex.login.domain.login.LoginService;
import com.wookis.ex.login.domain.member.Member;
import com.wookis.ex.login.web.SessionConst;
import com.wookis.ex.login.web.session.SessionManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    private final SessionManager sessionManager;

    @GetMapping("/login/sign-in")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "/login/sign-in/loginForm";
    }

//    @PostMapping("/login/sign-in")
    public String login(@Validated @ModelAttribute("loginForm") LoginForm loginForm,
                        BindingResult bindingResult,
                        HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "/login/sign-in/loginForm";
        }

        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 일치하지 않습니다.");
            return "/login/sign-in/loginForm";
        }

        //로그인 성공 처리
        //쿠키에 시간 정보를 주지 않으면 세션 쿠키(브라우저 종료 시 쿠키 삭제)
        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        response.addCookie(idCookie);

        return "redirect:/login";
    }

//    @PostMapping("/login/sign-in")
    public String loginV2(@Validated @ModelAttribute("loginForm") LoginForm loginForm,
                        BindingResult bindingResult,
                        HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "/login/sign-in/loginForm";
        }

        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 일치하지 않습니다.");
            return "/login/sign-in/loginForm";
        }

        //로그인 성공 처리
        //세션 관리자를 통해서 세션을 생성하고 회원 데이터 보관
        sessionManager.createSession(loginMember, response);

        return "redirect:/login";
    }

    @PostMapping("/login/sign-in")
    public String loginV3(@Validated @ModelAttribute("loginForm") LoginForm loginForm,
                          BindingResult bindingResult,
                          HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "/login/sign-in/loginForm";
        }

        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 일치하지 않습니다.");
            return "/login/sign-in/loginForm";
        }

        //로그인 성공 처리
        //세션이 있으면 세션 반환, 없으면 신규 세션을 생성

        // request.getSession(true) -> true가 default 이기 때문에 생략해도 됀다.
        // : 세션이 있으면 기존 세션을 반환하고, 없으면 새로운 세션을 생성해서 반환한다.

        // request.getSession(false)
        // : 세션이 있으면 기존 세션을 반환하지만 없으면 새로운 세션을 반환하지 않는다.
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);


        return "redirect:/login";
    }



//    @PostMapping("/login/logout")
    public String logout(HttpServletResponse response) {
        expireCookie(response, "memberId");
        return "redirect:/login";
    }

//    @PostMapping("/login/logout")
    public String logoutV2(HttpServletRequest request) {
        sessionManager.expire(request);
        return "redirect:/login";
    }

    @PostMapping("/login/logout")
    public String logoutV3(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        return "redirect:/login";
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
