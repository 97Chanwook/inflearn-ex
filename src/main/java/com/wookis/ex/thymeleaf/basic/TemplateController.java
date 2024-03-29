package com.wookis.ex.thymeleaf.basic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thymeleaf/template")
public class TemplateController {

    @GetMapping("/fragment")
    public String template() {
        return "thymeleaf/template/fragment/fragmentMain";
    }

    @GetMapping("/layout")
    public String layout() {
        return "thymeleaf/template/layout/layoutMain";
    }

    @GetMapping("/layoutExtend")
    public String layoutExtend() {
        return "thymeleaf/template/layoutExtend/layoutExtendMain";
    }
}
