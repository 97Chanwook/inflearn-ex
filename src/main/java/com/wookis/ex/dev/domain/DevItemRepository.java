package com.wookis.ex.dev.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DevItemRepository {

    private static Map<Long, DevItem> store = new HashMap<>();
    private static long sequence = 0L;

    public DevItem save(DevItem devItem) {
        devItem.setId(++sequence);
        store.put(devItem.getId(),devItem);
        return devItem;
    }

    public DevItem findById(Long id) {
        return store.get(id);
    }

    public List<DevItem> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long id, DevItem devItem) {
        DevItem item = findById(id);
        item.setItemName(devItem.getItemName());
        item.setPrice(devItem.getPrice());
        item.setQuantity(devItem.getQuantity());
        item.setOpen(devItem.isOpen());
        item.setRegions(devItem.getRegions());
        item.setItemType(devItem.getItemType());
        item.setDeliveryCode(devItem.getDeliveryCode());
    }

    public void clear() {
        store.clear();
    }

}
