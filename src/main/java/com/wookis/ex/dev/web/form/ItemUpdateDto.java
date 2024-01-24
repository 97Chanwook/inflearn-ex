package com.wookis.ex.dev.web.form;

import com.wookis.ex.dev.domain.DevItemType;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Data
public class ItemUpdateDto {

    @NotNull
    private Long id;

    @NotBlank
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    @NotNull
    @Max(value = 9999)
    private Integer quantity;

    private boolean open;

    @NotNull
    @Size(min = 1)
    private List<String> regions;

    @NotNull
    private DevItemType itemType;

    @NotBlank
    private String deliveryCode;
}
