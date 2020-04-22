package com.ankoye.jelly.base.result;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResultUtils {
    private static JSONObject mapper = new JSONObject();

    public static void responseWrite(HttpServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            response.getWriter().write(mapper.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}