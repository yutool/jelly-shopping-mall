package com.ankoye.jelly.file.controller;

import com.ankoye.jelly.base.result.Result;
import com.ankoye.jelly.file.service.FileService;
import com.ankoye.jelly.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author ankoye@qq.com
 */
@RestController
@RequestMapping("/com/ankoye/jelly/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public Result uploadFile(MultipartFile file) {
        String url = fileService.upload(file);
        return Result.success(url);
    }

    @PostMapping("/uploads")
    public Result uploadFile(MultipartFile[] files) {
        List<String> url = fileService.upload(files);
        return Result.success(url);
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(String res) throws IOException {
        Resource resource = new UrlResource(res);
        String ext = StringUtils.getFilenameExtension(resource.getURI().getPath());
        String contentType = FileUtils.contentType(ext);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
