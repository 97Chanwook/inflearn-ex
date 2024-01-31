package com.wookis.ex.login.web.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/login/session-info")
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return "세션이 없습니다.";
        }


        //세션 데이터 출력
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> {
                    log.info("session name {}, value ={}",name, session.getAttribute(name));
                });

        log.info("session Id = {}", session.getId());
        log.info("session Max Inactive Interval = {}",session.getMaxInactiveInterval());    //세션 유효 시간
        log.info("session creation Time = {}", new Date(session.getCreationTime()));
        log.info("session last Accessed Time = {}", new Date(session.getLastAccessedTime()));
        log.info("is New? = {}", session.isNew());


        return "OK";
    }
}
