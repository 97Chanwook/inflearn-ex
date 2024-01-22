package com.wookis.ex.validation.web;

import com.wookis.ex.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ItemValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        // item == clazz
        // item == item의 자식 클래스
        return Item.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target;

        //검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            //bindingResult.rejectValue(필드 명, 메시지 코드 첫 문장)
            errors.rejectValue("itemName", "required");
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            //bindingResult.rejectValue(필드 명, 메시지 코드 첫 문장, Argument, 기본 메시지)
            errors.rejectValue("price","range", new Object[]{1000, 1000000}, null);
        }

        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            errors.rejectValue("quantity","max", new Object[]{9999}, null);
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                //특정 필드가 없기 때문에 ObjectError 로 넘긴다.
                errors.reject("totalPriceMin", new Object[]{10000}, null);
            }
        }
    }
}
