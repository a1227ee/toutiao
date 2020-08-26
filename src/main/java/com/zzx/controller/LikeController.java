package com.zzx.controller;

import com.zzx.async.EventModel;
import com.zzx.async.EventProducer;
import com.zzx.async.EventType;
import com.zzx.beans.*;
import com.zzx.service.BlogService;
import com.zzx.service.LikeService;
import com.zzx.util.RequestIpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


/**
 * @ClassName LikeController
 * @Deacription:
 * @Author zzx
 * @Date 2020/3/4 15:58
 **/
@RestController
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class LikeController {
    @Autowired
    HostHolder hostHolder;
    @Autowired
    LikeService likeService;
    @Autowired
    EventProducer eventProducer;
    @Autowired
    BlogService blogService;

    @RequestMapping(path = {"/like"},method = {RequestMethod.POST})
    public String like(@RequestParam("blogId") int blogId, HttpServletRequest request){
            User user = hostHolder.getUser();
            if (user==null){
                return "500";
            }
            Blog blog = blogService.getBlog(blogId);

            long like = likeService.like(String.valueOf(user.getId()), blogId );

            //进行异步
            // eventProducer.fireEvent(new EventModel(EventType.LIKE)
            //.setActorId(user.getId()).setEntityId(blogId).setEntityType(EntityType.ENTITY_NEWS)
            //.setEntityOwnerId(blog.getUserId()));

            return String.valueOf(like);

    }

    @RequestMapping(path = {"/likeSum"} )
    public msg getLickStatus(@RequestParam("blogId") int blogId, HttpServletRequest request){
        HashMap<String,Long> map = new HashMap<>();
        long like=0;
        User user = hostHolder.getUser();
        if (user!=null){
              like = likeService.getLickStatus(String.valueOf(user.getId()), blogId );
        }else {
            String ip = RequestIpUtil.getIp(request);
              like = likeService.getLickStatus(ip,blogId);
        }
        map.put("likeStatus",  like );
        map.put("likeNumber",likeService.getNumber(blogId));
        return msg.success().add("map",map);
    }



}
