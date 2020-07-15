package com.ankoye.jelly.file.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ankoye@qq.com
 */
@Data
@AllArgsConstructor
public class FastDFSFile {
    //文件名字
    private String name;
    //文件内容
    private byte[] content;
    //文件扩展名
    private String ext;
    //文件MD5摘要值
    private String md5;
    //文件创建作者
    private String author;

    public FastDFSFile(String name, byte[] content, String ext) {
        this.name = name;
        this.content = content;
        this.ext = ext;
    }
}
