package com.zzx.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @ClassName test
 * @Deacription:
 * @Author zzx
 * @Date 2020/1/18 13:11
 **/
@CrossOrigin(allowCredentials="true",maxAge = 3600)
@RestController
public class TokenController {

    @RequestMapping("/getToken")
    public String getToken(HttpSession session){
        String uuid = UUID.randomUUID().toString();
        session.setAttribute("token",uuid);
        return uuid;
    }

}
