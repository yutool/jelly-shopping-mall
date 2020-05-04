package com.ankoye.jelly.util;

public class FileUtils {
    public static String contentType(String ext) {

        if (ext.equals("BMP") || ext.equals("bmp")
                || ext.toUpperCase().equals("BMP")) {
            return "image/bmp";
        }
        if (ext.equals("GIF") || ext.equals("gif")
                || ext.toUpperCase().equals("GIF")) {
            return "image/gif";
        }
        if (ext.equals("JPEG") || ext.equals("jpeg") || ext.equals("JPG")
                || ext.equals("jpg") || ext.equals("PNG")
                || ext.equals("png") || ext.toUpperCase().equals("JPEG")
                || ext.toUpperCase().equals("JPG") || ext.toUpperCase().equals("PNG")) {
            return "image/jpeg";
        }
        if (ext.equals("HTML") || ext.equals("html")) {
            return "text/html";
        }
        if (ext.equals("TXT") || ext.equals("txt")
                || ext.toUpperCase().equals("TXT")) {
            return "text/plain";
        }
        if (ext.equals("VSD") || ext.equals("vsd")
                || ext.toUpperCase().equals("VSD")) {
            return "application/vnd.visio";
        }
        if (ext.equals("PPTX") || ext.equals("pptx") || ext.equals("PPT")
                || ext.equals("ppt") || ext.toUpperCase().equals("PPTX")
                || ext.toUpperCase().equals("PPT")) {
            return "application/vnd.ms-powerpoint";
        }
        if (ext.equals("DOCX") || ext.equals("docx") || ext.equals("DOC")
                || ext.equals("doc") || ext.toUpperCase().equals("DOCX")
                || ext.toUpperCase().equals("DOC")) {
            return "application/msword";
        }
        if (ext.equals("XML") || ext.equals("xml")
                || ext.toUpperCase().equals("XML")) {
            return "text/xml";
        }
        if (ext.equals("pdf") || ext.equals("PDF")
                || ext.toUpperCase().equals("PDF")) {
            return "application/pdf";
        }
        return "application/octet-stream";
    }
}
