package com.wookis.ex.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wookis.ex.exception.exception.UserException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

    private final ObjectMapper mapper = new ObjectMapper();


    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        try {
            if (ex instanceof UserException) {
                log.info("User Exception Resolver to 400");
                String acceptHeader = request.getHeader("accept");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                if (acceptHeader.equals(MediaType.APPLICATION_JSON_VALUE)) {
                    //1. application/json의 경우
                    Map<String, Object> errorResult = new HashMap<>();
                    errorResult.put("ex", ex.getClass());
                    errorResult.put("message",ex.getMessage());

                    String result = mapper.writeValueAsString(errorResult);

                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(result);

                    return new ModelAndView();
                } else {
                    //2. text/html의 경우
                    return new ModelAndView("error/500");
                }
            }
        } catch (IOException e) {
            log.info("resolver ex",e);
        }

        return null;
    }
}
