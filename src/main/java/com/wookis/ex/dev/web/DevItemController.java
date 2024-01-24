package com.wookis.ex.dev.web;

import com.wookis.ex.dev.domain.DevDeliveryCode;
import com.wookis.ex.dev.domain.DevItem;
import com.wookis.ex.dev.domain.DevItemRepository;
import com.wookis.ex.dev.domain.DevItemType;
import com.wookis.ex.dev.web.form.ItemSaveDto;
import com.wookis.ex.dev.web.form.ItemUpdateDto;
import com.wookis.ex.domain.item.DeliveryCode;
import com.wookis.ex.domain.item.Item;
import com.wookis.ex.domain.item.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/dev/items")
public class DevItemController {

    private final DevItemRepository repository;

    @ModelAttribute("regions")
    public Map<String, String> regions() {
        Map<String,String> regions = new LinkedHashMap<>();
        regions.put("SEOUL","서울");
        regions.put("BUSAN","부산");
        regions.put("JEJU","제주");

        return regions;
    }

    @ModelAttribute("itemTypes")
    public DevItemType[] itemTypes() {
        return DevItemType.values();
    }

    @ModelAttribute("deliveryCodes")
    public List<DevDeliveryCode> deliveryCodes() {
        return List.of(
                new DevDeliveryCode("FAST","빠른 배송"),
                new DevDeliveryCode("NORMAL","일반 배송"),
                new DevDeliveryCode("SLOW","느린 배송")
        );
    }

    @GetMapping
    public String items(Model model) {
        List<DevItem> items = repository.findAll();
        model.addAttribute("items",items);
        return "dev/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") Long itemId, Model model) {
        DevItem item = repository.findById(itemId);
        model.addAttribute("item",item);
        return "dev/item";
    }

    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("item",new Item());
        return "dev/addForm";
    }

    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute("item")ItemSaveDto form,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (form.getPrice() != null && form.getQuantity() != null ) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin",new Object[]{10000, resultPrice},null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}",bindingResult);
            return "dev/addForm";
        }

        DevItem devItem = new DevItem(form.getItemName(),
                form.getPrice(),
                form.getQuantity(),
                form.isOpen(),
                form.getRegions(),
                form.getItemType(),
                form.getDeliveryCode());

        DevItem savedItem = repository.save(devItem);
        redirectAttributes.addAttribute("itemId",savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/dev/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable("itemId") Long itemId, Model model) {
        DevItem item = repository.findById(itemId);
        model.addAttribute("item",item);
        return "dev/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable("itemId") Long id,
                       @Validated @ModelAttribute("item") ItemUpdateDto form,
                       BindingResult bindingResult) {

        if (form.getPrice() != null && form.getQuantity() != null ) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin",new Object[]{10000, resultPrice},null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}",bindingResult);
            return "dev/addForm";
        }

        DevItem devItem = new DevItem(form.getItemName(),
                form.getPrice(),
                form.getQuantity(),
                form.isOpen(),
                form.getRegions(),
                form.getItemType(),
                form.getDeliveryCode());

        repository.update(id, devItem);
        return "redirect:/dev/items/{itemId}";
    }
}
