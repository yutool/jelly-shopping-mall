package com.ankoye.jelly.common.result;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ankoye@qq.com
 */
public class ResultUtils {
    public static void responseWrite(HttpServletResponse response, Result result) {
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}