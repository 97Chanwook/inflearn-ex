package com.wookis.ex.typeconverter.controller;

import com.wookis.ex.typeconverter.type.IpPort;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/converter")
public class HelloController {

    @GetMapping("/hello-v1")
    public String hello(HttpServletRequest request) {
        String data = request.getParameter("data"); //문자 타입 조회
        int intValue = Integer.parseInt(data);

        System.out.println("intValue = " + intValue);
        return "OK";
    }

    @GetMapping("/hello-v2")
    public String helloV2(@RequestParam Integer data) {
        System.out.println("data = " + data);
        return "OK";
    }

    @GetMapping("/ip-port")
    public String ipPort(@RequestParam IpPort ipPort) {
        System.out.println("ipPort = " + ipPort);
        return "OK";
    }

}
