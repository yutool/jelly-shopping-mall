package com.ankoye.jelly.file.controller;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public Result uploadFile(MultipartFile file) {
        String url = fileService.upload(file);
        return Result.success(url);
    }
}
