package com.mallfe.item.controller;

import com.mallfe.common.json.JsonObject;
import com.mallfe.item.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 描述
 *
 * @author yangbin
 * @since 2019/9/14
 */
@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 上传图片
     * @param file 图片文件
     * @return
     */
    @PostMapping("image")
    public ResponseEntity<String> uploadImage(@RequestParam("file")MultipartFile file){
        return ResponseEntity.ok(uploadService.uploadImage(file));
    }

    @PostMapping("imagejson")
    public JsonObject uploadImageReturnJosn(@RequestParam("file")MultipartFile file){
        return uploadService.uploadImageReturnJson(file);
    }

}
