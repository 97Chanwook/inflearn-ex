package com.wookis.ex.exception.api;

import com.wookis.ex.exception.exception.UserException;
import com.wookis.ex.exception.exhandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/exception")
public class ApiExceptionV2Controller {

    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {
        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자 입니다.");
        }

        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값입니다.");
        }

        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류.");
        }

        return new MemberDto(id, "hello spring");
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }
}
