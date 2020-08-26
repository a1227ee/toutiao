package com.zzx.controller;

import com.zzx.beans.msg;
import com.zzx.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;


/**
 * @ClassName LoginController
 * @Deacription:
 * @Author zzx
 * @Date 2020/1/10 19:29
 **/
@CrossOrigin(allowCredentials="true",maxAge = 3600)
@RestController
public class LoginController {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService userService;


    @RequestMapping(path = {"/reg"},method = RequestMethod.POST)
    public msg reg(@RequestParam("username") String username, @RequestParam("password")String password,
                   @RequestParam(value="rember",defaultValue = "0") int rememberme
    , HttpServletResponse response, @RequestParam("yzm") String yzm, HttpSession session){
        Map<String, Object> map;
        try {
            map = userService.register(username, password);
            if (map.containsKey("ticket")){
                Cookie cookie=new Cookie("ticket",map.get("ticket").toString());
                cookie.setPath("/");

                if (rememberme>0){
                    cookie.setMaxAge(3600*24*5);
                }
                response.addCookie(cookie);
                session.setAttribute("token",null);
                return msg.success("注册成功");
            }
        }catch (Exception ex){
            logger.error("注册异常"+ex.getMessage());
            return msg.fail("注册失败");
        }
        return msg.fail("注册失败:用户名存在").add("map",map);
    }

    @RequestMapping("/login")
    public msg login(@RequestParam("username") String username,@RequestParam("password")String password,
                   @RequestParam(value="rember",defaultValue = "0") int rememberme, HttpServletResponse response){
        Map<String, Object> map;
        try {
            map = userService.login(username, password);
            if (map.containsKey("ticket")){
                Cookie cookie=new Cookie("ticket",map.get("ticket").toString());
                cookie.setPath("/");

                if (rememberme>0){
                    cookie.setMaxAge(3600*24*5);
                }
                response.addCookie(cookie);
                return msg.success("登录成功");
            }
        }catch (Exception ex){
            logger.error("登录异常"+ex.getMessage());
            return msg.fail("登录异常");
        }
        return msg.fail().add("map",map);
    }
    //退出,修改ticket的状态
    @RequestMapping("/logout")
    public msg logout(@CookieValue("ticket")String ticket){
        userService.logout(ticket);
        return msg.success("返回首页");
    }




}
