package com.wookis.ex.upload.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/upload/spring")
public class SpringUploadController {

    private final String fileDir = System.getProperty("user.dir")+"/files/";

    @GetMapping("/upload")
    public String newFile() {
        return "upload/upload-form";
    }

    @PostMapping("/upload")
    public String saveFile(@RequestParam("itemName") String itemName,
                           @RequestParam("file") MultipartFile file,
                           HttpServletRequest request) throws IOException {

        if (!file.isEmpty()) {
            String fullPath = fileDir + file.getOriginalFilename();
            log.info("파일 저장 File Full Path = {}", fullPath);
            log.info("File Type : {}",file.getContentType());
            file.transferTo(new File(fullPath));
        }

        return "upload/upload-form";
    }

}
