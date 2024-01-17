package com.wookis.ex.thymeleaf.basic;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/thymeleaf/basic")
public class BasicController {



    @GetMapping("/text-basic")
    public String textBasic(Model model) {
        model.addAttribute("data","Hello Spring");
        return "thymeleaf/basic/text-basic";
    }

    @GetMapping("/text-unescaped")
    public String textUnescaped(Model model) {
        model.addAttribute("data","Hello <b>Spring!</b>");
        return "thymeleaf/basic/text-unescaped";
    }

    /**
     * SpringEL - 변수 표현식
     */
    @GetMapping("/variable")
    public String variable(Model model) {
        User userA = new User("userA", 10);
        User userB = new User("userB", 20);

        List<User> users = new ArrayList<>();
        users.add(userA);
        users.add(userB);

        Map<String, User> map = new HashMap<>();
        map.put("userA",userA);
        map.put("userB",userB);

        model.addAttribute("user",userA);
        model.addAttribute("users",users);
        model.addAttribute("userMap",map);

        return "thymeleaf/basic/variable";
    }

    @GetMapping("/basic-objects")
    public String basicObjects(HttpSession session, HttpServletRequest request,
                               HttpServletResponse response, Model model){

        session.setAttribute("sessionData","Hello Session");

        model.addAttribute("request",request);
        model.addAttribute("response",response);
        model.addAttribute("session",session);
        model.addAttribute("servletContext",request.getServletContext());

        return "thymeleaf/basic/basic-objects";
    }

    @GetMapping("/date")
    public String date(Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "thymeleaf/basic/date";
    }

    @GetMapping("/link")
    public String link(Model model) {
        model.addAttribute("param1","data1");
        model.addAttribute("param2","data2");
        return "thymeleaf/basic/link";
    }

    //리터럴
    // 타임리프에서 문자 리터럴은 작은 따옴표('')로 꼭 감싸야 한다.
    @GetMapping("/literal")
    public String literal(Model model) {
        model.addAttribute("data","Hello Spring");

        return "thymeleaf/basic/literal";
    }

    @GetMapping("operation")
    public String operation(Model model) {
        model.addAttribute("nullData", null);
        model.addAttribute("data", "Spring!");

        return "thymeleaf/basic/operation";
    }

    //속성 값
    @GetMapping("/attribute")
    public String attribute() {
        return "thymeleaf/basic/attribute";
    }

    //반복문
    @GetMapping("/each")
    public String each(Model model) {
        addUsers(model);
        return "thymeleaf/basic/each";
    }

    @GetMapping("/condition")
    public String condition(Model model) {
        addUsers(model);
        return "thymeleaf/basic/condition";
    }

    @GetMapping("/comments")
    public String comments(Model model) {
        model.addAttribute("data","Spring!");
        return "thymeleaf/basic/comments";
    }

    @GetMapping("/block")
    public String block(Model model) {
        addUsers(model);
        return "thymeleaf/basic/block";
    }

    @GetMapping("/javascript")
    public String javascript(Model model) {
        model.addAttribute("user", new User("userA", 10));
        addUsers(model);

        return "thymeleaf/basic/javascript";
    }

    private void addUsers(Model model) {
        model.addAttribute("users",List.of(
                new User("UserA", 10),
                new User("UserB", 20),
                new User("UserC", 30)
        ));
    }

    @Component("helloBean")
    public class HelloBean {
        public String hello(String data) {
            return "Hello "+data;
        }
    }

    //Sample Data 를 위한 클래스 생성
    @Data
    static class User {
        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }



}
