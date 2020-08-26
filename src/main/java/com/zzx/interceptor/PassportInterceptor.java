package com.zzx.interceptor;

import com.zzx.beans.HostHolder;
import com.zzx.beans.LoginTicket;
import com.zzx.beans.User;
import com.zzx.dao.LoginTicketDao;
import com.zzx.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


/**
 * @ClassName PassportInterceptor
 * @Deacription:
 * @Author zzx
 * @Date 2020/1/11 0:04
 **/
@Component
public class PassportInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UserDao userDao;
    @Autowired
    private LoginTicketDao loginTicketDao;
    @Autowired
    HostHolder hostHolder;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       String ticket=null;
       if (request.getCookies()!=null){
           for (Cookie cookie:request.getCookies()){
               if (cookie.getName().equals("ticket")){
                   ticket=cookie.getValue();
                   break;
               }
           }
       }
       if (ticket!=null){
           LoginTicket loginTicket=loginTicketDao.selectByTicket(ticket);
           if (loginTicket==null||loginTicket.getExpired().before(new Date())||loginTicket.getStatus()!=0){

               return true;
           }


           User user=userDao.selectById(loginTicket.getUserId());
            hostHolder.setUser(user);
       }
        return super.preHandle(request, response, handler);
    }



    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
