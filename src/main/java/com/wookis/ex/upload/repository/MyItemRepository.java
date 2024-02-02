package com.wookis.ex.upload.repository;

import com.wookis.ex.upload.domain.MyItem;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MyItemRepository {

    private final Map<Long, MyItem> store = new HashMap<>();
    private long sequence = 0L;

    public MyItem save(MyItem item) {
        item.setId(++sequence);
        store.put(item.getId(),item);
        return item;
    }

    public MyItem findById(Long id) {
        return store.get(id);
    }
}
