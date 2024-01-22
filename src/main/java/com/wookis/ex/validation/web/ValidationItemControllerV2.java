package com.wookis.ex.validation.web;

import com.wookis.ex.domain.item.Item;
import com.wookis.ex.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    //컨트롤러 내부 메소드가 호출될 때마다 호출됌
    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(itemValidator);
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

    //BindingResult는 항상 @ModelAttribute로 넘어온 모델 뒤에 선언해야한다.
//    @PostMapping("/add")
    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes, Model model) {

        //검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            //new FieldError("Object","field","message")
            bindingResult.addError(new FieldError("item","itemName","상품 이름은 필수 입니다."));
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item","price","가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }

        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item","quantity","수량은 최대 9,999 까지 허용 합니다."));
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                //특정 필드가 없기 때문에 ObjectError 로 넘긴다.
                bindingResult.addError(new ObjectError("item","가격 * 수량의 합은 10,000원 이상이어야 합니다."));
            }
        }

        // 검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.error("Errors = {}",bindingResult);
            return "validation/v2/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    // - FiledError 파라미터 목록
    //'objectName' : 오류가 발생한 객체의 이름
    //'field' : 오류 필드
    //'rejectedValue' : 사용자가 입력한 값
    //'bindingFailure' : 타입 오류 같은 바인딩 실패인지, 검증 실패인지 구분 값
    //'codes' : 메시지 코드
    //'argument' : 메ㅣ지에서 사용하는 인자
    //'defaultMessage' : 기본 오류 메시지

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes, Model model) {

        //검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            //new FieldError("Object","field", "거절된 값", "데이터가 넘어오지 않았냐 여부", null, null, "message")
            bindingResult.addError(new FieldError("item","itemName",item.getItemName(),false, null, null,"상품 이름은 필수 입니다."));
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item","price",item.getPrice(),false,null,null,"가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }

        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item","quantity",item.getPrice(),false,null,null,"수량은 최대 9,999 까지 허용 합니다."));
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                //특정 필드가 없기 때문에 ObjectError 로 넘긴다.
                bindingResult.addError(new ObjectError("item",null,null,"가격 * 수량의 합은 10,000원 이상이어야 합니다."));
            }
        }

        // 검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.error("Errors = {}",bindingResult);
            return "validation/v2/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }


//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes, Model model) {

        //검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            //new FieldError("Object","field", "거절된 값", "데이터가 넘어오지 않았냐 여부", "메시지 코드(errors.properties)", null, "message")
            bindingResult.addError(new FieldError("item","itemName",item.getItemName(),false,
                    new String[] {"required.item.itemName"}, null, null));
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item","price",item.getPrice(),false,
                    new String[] {"range.item.price"}, new Object[]{1000,1000000}, null));
        }

        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item","quantity",item.getPrice(),false,
                    new String[] {"max.item.quantity"}, new Object[]{9999}, null));
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                //특정 필드가 없기 때문에 ObjectError 로 넘긴다.
                bindingResult.addError(new ObjectError("item",new String[]{"totalPriceMin"},new Object[]{10000},null));
            }
        }

        // 검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.error("Errors = {}",bindingResult);
            return "validation/v2/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }


//    @PostMapping("/add")
    public String addItemV4(@ModelAttribute Item item, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes, Model model) {

        //  bindingResult.rejectValue("itemName", "required"); 를 사용하게 되면 Level1부터 그 아래로 순서대로 찾는다
        // ex) 필드 오류의 경우
        //[
        // required.item.itemName, (Level1)
        // required.itemName,   (Level2)
        // required.java.lang.String,   (Level3)
        // required     (Level4)
        // ]
        // ex) 객체 오류의 경우
        //[
        //  required.item,
        //  required
        //]

        //검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            //bindingResult.rejectValue(필드 명, 메시지 코드 첫 문장)
            bindingResult.rejectValue("itemName", "required");
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            //bindingResult.rejectValue(필드 명, 메시지 코드 첫 문장, Argument, 기본 메시지)
            bindingResult.rejectValue("price","range", new Object[]{1000, 1000000}, null);
        }

        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.rejectValue("quantity","max", new Object[]{9999}, null);
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                //특정 필드가 없기 때문에 ObjectError 로 넘긴다.
                bindingResult.reject("totalPriceMin", new Object[]{10000}, null);
            }
        }

        // 검증에 실패하면 다시 입력 폼으로
        // 레벨 별로 로그가 나온다.
        if (bindingResult.hasErrors()) {
            log.error("Errors = {}",bindingResult);
            return "validation/v2/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

//    @PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes, Model model) {

        itemValidator.validate(item,bindingResult);
        if (bindingResult.hasErrors()) {
            log.error("Errors = {}",bindingResult);
            return "validation/v2/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    //@ModelAttribute 앞에 @Validated 추가
    // - Item에 대해서 자동으로 검증이 수행된다.
    // - WebDataBinder에 검증기를 추가해야함.
    // - 현재 @InitBinder 를 통해 추가해둠
    @PostMapping("/add")
    public String addItemV6(@Validated @ModelAttribute Item item, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes, Model model) {

        if (bindingResult.hasErrors()) {
            log.error("Errors = {}",bindingResult);
            return "validation/v2/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}

