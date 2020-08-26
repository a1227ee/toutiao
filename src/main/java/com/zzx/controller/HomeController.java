package com.zzx.controller;

import com.zzx.beans.HostHolder;
import com.zzx.beans.News;
import com.zzx.service.NewsService;
import com.zzx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName HomeController
 * @Deacription:
 * @Author zzx
 * @Date 2020/1/9 12:12
 **/
@RestController
public class HomeController {

    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;
    public List getNews(int userId,int offset,int limit){
        List<News> newsList=newsService.getLatesNews(userId,offset,limit);
        List<Object> vos=new ArrayList<>();
        for (News news:newsList){
            Map<String,Object> map=new HashMap();
            map.put("news",news);
            map.put("user",userService.getUser(news.getUserId()));
            vos.add(map);
        }
        Map<String,Object> map=new HashMap();
        map.put("userTicket",hostHolder.getUser());vos.add(map);
        return vos;
    }

    @RequestMapping(path = {"/index"})
    public List index(){
        return getNews(0,0,10);
    }

    @RequestMapping(path = {"/user/{userId}"})
    public List index(@PathVariable int userId){
        return getNews(userId,0,10);
    }

    @RequestMapping("admin/123")
    public String index1( ){
        return "我是一个大帅哥";
    }

}
