package com.zzx.controller;

import com.zzx.beans.msg;
import com.zzx.service.QiniuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName UtilController
 * @Deacription:
 * @Author zzx
 * @Date 2020/1/14 18:18
 **/
@CrossOrigin
@RestController
public class UtilController {
    @Autowired
    QiniuService qiniuService;

    Map<String,String> phoneMap=new ConcurrentHashMap<String,String>();

   // @RequestMapping(path = {"/send/message/{phone}"})
    public msg sendMessage(@PathVariable("phone") String phone){
        boolean ph = phoneMap.containsKey(phone);
        if (ph){
            return msg.fail("已经发送过短信");
        }else {
            phoneMap.put(phone,"0");
        }
        int code=(int)(1000000*Math.random());
        Map<String,String>  map=new HashMap<String,String>();
        map.put("code",code+"");
        boolean b = qiniuService.sendMessage(map, phone);
        if (b){
            return msg.success().add("code",code);
        }else{
            return msg.fail("发送短信失败");
        }
    }

}
