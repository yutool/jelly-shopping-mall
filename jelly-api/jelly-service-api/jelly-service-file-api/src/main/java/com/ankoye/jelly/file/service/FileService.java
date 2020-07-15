package com.ankoye.jelly.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ankoye@qq.com
 */
public interface FileService {

    String upload(MultipartFile file);

    List<String> upload(MultipartFile[] files);

}
