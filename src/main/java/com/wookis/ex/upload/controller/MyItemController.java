package com.wookis.ex.upload.controller;

import com.wookis.ex.upload.domain.MyItem;
import com.wookis.ex.upload.domain.UploadFile;
import com.wookis.ex.upload.file.FileStore;
import com.wookis.ex.upload.repository.MyItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/upload")
@RequiredArgsConstructor
public class MyItemController {

    private final MyItemRepository repository;
    private final FileStore store;


    @GetMapping("/items/new")
    public String newItem(@ModelAttribute("form") MyItemForm form) {
        return "upload/item-form";
    }

    @PostMapping("/items/new")
    public String saveItem(@ModelAttribute("form") MyItemForm form,
                           RedirectAttributes redirectAttributes) throws IOException {

        UploadFile uploadFile = store.storeFile(form.getAttachFile());
        List<UploadFile> storeUploadFiles = store.storeFiles(form.getUploadFiles());

        //DB에 저장
        MyItem myItem = new MyItem();
        myItem.setItemName(form.getItemName());
        myItem.setAttachFile(uploadFile);
        myItem.setUploadFiles(storeUploadFiles);

        MyItem save = repository.save(myItem);

        redirectAttributes.addAttribute("itemId",save.getId());

        return "redirect:/upload/items/{itemId}";
    }

    @GetMapping("/items/{itemId}")
    public String items(@PathVariable("itemId") Long id, Model model) {
        MyItem item = repository.findById(id);
        model.addAttribute("item",item);

        return "upload/item-view";
    }

    //파일(이미지) 보여주기
    @ResponseBody
    @GetMapping("/images/{fileName}")
    public Resource downloadImage(@PathVariable("fileName") String fileName) throws MalformedURLException {
        //TODO 보안 로직이 필요함
        return new UrlResource("file:" + store.getFullPath(fileName));
    }

    // 파일 다운로드
    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable("itemId") Long itemId) throws MalformedURLException {
        MyItem item = repository.findById(itemId);
        String storeFileName = item.getAttachFile().getStoreFileName();
        String uploadFileName = item.getAttachFile().getUploadFileName();

        UrlResource urlResource = new UrlResource("file:" + store.getFullPath(storeFileName));

        log.info("Upload File Name = {}", uploadFileName);
        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName +"\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }
}
