package com.ankoye.jelly.oauth;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义权限不足需要做的业务操作
 * 当用户登录系统后访问资源时因权限不足则会进入到此类并执行相关业务
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding("UTF-8");
        StringBuffer msg = new StringBuffer("请求: ");
        msg.append(request.getRequestURI()).append(" 权限不足，无法访问系统资源.");
        response.getWriter().write(msg.toString());

       /* boolean ajaxRequest = HttpUtils.isAjaxRequest(request);
        if (ajaxRequest){
            //如果是ajax请求 则返回403错
            ResultUtil.writeJavaScript(response,ErrorCodeEnum.AUTHORITY,msg.toString());
        }else {
            // 非ajax请求 则跳转到指定的403页面
            //此处省略...................
        }*/
    }
}