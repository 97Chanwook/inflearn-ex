package com.wookis.ex.upload.domain;

import lombok.Data;

import java.util.List;

@Data
public class MyItem {

    private Long id;
    private String itemName;
    private UploadFile attachFile;
    private List<UploadFile> uploadFiles;
}
