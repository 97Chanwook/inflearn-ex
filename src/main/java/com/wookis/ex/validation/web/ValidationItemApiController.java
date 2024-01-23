package com.wookis.ex.validation.web;

import com.wookis.ex.domain.ItemSaveForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/validation/api/items")
public class ValidationItemApiController {

    //@ModelAttribute : Parameter
    // - 필드 단위로 정교하게 바인딩이 적용된다.
    // - 특정 필드가 바인딩 되지 않아도 나머지 필드는 정상적으로 바인딩 되고 Validator 를 사용하여 검증 가능

    //@RequestBody : Body
    // - HttpMessageConverter 단계에서 JSON 데이터를 객체로 변경하지 못하면 이후 단계 자체가 진행되지 않고 예외가 발생하고,
    // - 컨트롤로도 호출되지 않고, Validator도 적용할 수 없다.

    //API의 3가지 경우를 나누어 생각해야 한다.
    // 1. 성공 요청 : 성공
    // 2. 실패 요청 : JSON을 객체로 생성하는 것 자체가 실패할 경우
    // 3. 검증 오류 요청 : JSON을 객체로 생성하는 것은 성공했고, 검증에서 실패할 경우
    @PostMapping("/add")
    public Object addItem(@Validated @RequestBody ItemSaveForm form, BindingResult bindingResult) {
        log.info("API 컨트롤러 호출");

        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생 errors = {}",bindingResult);
            return bindingResult.getAllErrors();
        }

        log.info("성공 로직 실행");
        return form;
    }
}
