package com.wookis.ex.exception.exhandler.advice;

import com.wookis.ex.exception.api.ApiExceptionV2Controller;
import com.wookis.ex.exception.exception.UserException;
import com.wookis.ex.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 대상을 지정하지 않으면 모든 controller 가 대상이 된다.
 *
 * 대상 지정 방법
 * 1) @ControllerAdvice(annotaions=RestController.class) -> 어노테이션 지정
 * 2) @ControllerAdvice("org.example.controller") -> 패키지 지정
 * 3) @ControllerAdvice(assignableTypes = {ControllerInterface.class, AbstractController.class} -> 컨트롤러 클래스 직접 지정
 */

@Slf4j
@RestControllerAdvice(assignableTypes = ApiExceptionV2Controller.class)
public class exControllerAdvice {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[Exception Handler] ex",e);

        return new ErrorResult("BAD",e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.error("[Exception Handler] ex",e);

        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[Exception Handler] ex",e);
        return new ErrorResult("EX","내부 오류");
    }
}
