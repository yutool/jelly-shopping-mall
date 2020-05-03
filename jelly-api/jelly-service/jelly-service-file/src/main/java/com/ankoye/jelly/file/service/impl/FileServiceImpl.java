package com.ankoye.jelly.file.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ankoye.jelly.file.model.FastDFSFile;
import com.ankoye.jelly.file.service.FileService;
import com.ankoye.jelly.file.util.FastDFSClient;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Component
public class FileServiceImpl implements FileService {
    public String upload(MultipartFile file) {
        try {
            // 判断文件是否存在
            if (file == null){
                throw new RuntimeException("文件不存在");
            }
            System.out.println();

            // 获取文件的完整名称
            String originalFilename = file.getOriginalFilename();
            if (StringUtils.isEmpty(originalFilename)){
                throw new RuntimeException("文件不存在");
            }

            // 获取文件的扩展名称
            String extName = StringUtils.getFilenameExtension(file.getOriginalFilename());

            // 获取文件内容
            byte[] content = file.getBytes();

            // 创建文件上传的封装实体类
            FastDFSFile fastDFSFile = new FastDFSFile(originalFilename, content, extName);

            // 基于工具类进行文件上传,并接受返回参数  String[]
            String[] uploadResult = FastDFSClient.upload(fastDFSFile);

            // 封装返回结果
            String url = FastDFSClient.getTrackerUrl() + uploadResult[0] + "/" + uploadResult[1];
            return url;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
