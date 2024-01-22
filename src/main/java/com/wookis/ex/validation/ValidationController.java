package com.wookis.ex.validation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/validation")
public class ValidationController {

    @GetMapping
    public String indexHtml() {
        return "validation/validation_index";
    }
}
