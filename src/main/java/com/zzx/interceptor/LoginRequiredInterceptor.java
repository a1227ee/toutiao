package com.zzx.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.zzx.beans.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.PrintWriter;

/**
 * @ClassName LoginRequiredInterceptor
 * @Deacription:用于判断是否登录没有（权限判断）
 * @Author zzx
 * @Date 2020/1/12 1:51
 **/
@Component
public class LoginRequiredInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null ;

        try {
            if (hostHolder.getUser() == null) {
                JSONObject res = new JSONObject();
                res.put("success", "false");
                res.put("msg", "请先登录");
                out = response.getWriter();
                out.append(res.toString());
                return false;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(500);
            return false;
        }
        return true;
    }
}
