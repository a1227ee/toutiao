package com.zzx.controller;

import com.zzx.beans.HostHolder;
import com.zzx.beans.User;
import com.zzx.beans.msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Deacription:专门用来传递登录的用户信息
 * @Author zzx
 * @Date 2020/1/18 21:11
 **/
@CrossOrigin(allowCredentials="true",maxAge = 3600)
@RestController
public class UserController {
    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/getUser"})
    public msg getUser(){
        User user = hostHolder.getUser();

        if (user==null){

            return msg.fail("您还没有登录，请登录");

        }
        user.setPassword("******");
        user.setSalt("*********");
        return msg.success("登录成功").add("user",user);
    }
}
