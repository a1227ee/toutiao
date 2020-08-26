package com.zzx.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.zzx.beans.HostHolder;
import com.zzx.beans.LoginTicket;
import com.zzx.beans.User;
import com.zzx.dao.LoginTicketDao;
import com.zzx.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;


/**
 * @ClassName PassportInterceptor
 * @Deacription:
 * @Author zzx
 * @Date 2020/1/11 0:04
 **/
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null ;


        String token = request.getParameter("token");
        Object token1 = request.getSession().getAttribute("token");
        if (token==null||!token.equals((String) token1)){
            JSONObject res = new JSONObject();
            res.put("success", "false");
            res.put("msg", "请勿重复提交(请从网页提交)");
            out = response.getWriter();
            out.append(res.toString());
            return  false;
        }
        return super.preHandle(request, response, handler);
    }
}
