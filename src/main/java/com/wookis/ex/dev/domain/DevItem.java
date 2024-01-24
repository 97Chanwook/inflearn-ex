package com.wookis.ex.dev.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DevItem {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;
    private boolean open;
    private List<String> regions;
    private DevItemType itemType;
    private String deliveryCode;


    public DevItem(String itemName, Integer price, Integer quantity, boolean open, List<String> regions, DevItemType itemType, String deliveryCode) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.open = open;
        this.regions = regions;
        this.itemType = itemType;
        this.deliveryCode = deliveryCode;
    }
}
