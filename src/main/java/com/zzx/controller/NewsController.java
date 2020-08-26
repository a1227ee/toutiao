package com.zzx.controller;


import com.zzx.beans.*;
import com.zzx.service.CommentService;
import com.zzx.service.NewsService;
import com.zzx.service.QiniuService;
import com.zzx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @ClassName NewsController
 * @Deacription:
 * @Author zzx
 * @Date 2020/1/12 13:17
 **/
@CrossOrigin
@RestController
public class NewsController {
    /**
     *
     *
    @Autowired
    NewsService newsService;
    @Autowired
    QiniuService qiniuService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;

    @RequestMapping(path = {"/news/{newsId}"},method = {RequestMethod.GET})
    public msg newDetail(@PathVariable("newsId")int newsId){
        News news = newsService.getById(newsId);
        List arraylist=new ArrayList();
        if (news!=null){
            List<Comment> comments = commentService.getCommentsByEntity(news.getId(), EntityType.ENTITY_NEWS);

            for (Comment comment:comments){
                Map map=new HashMap ();
                map.put("comment",comment);
                map.put("user",userService.getUser(comment.getUserId()));
                arraylist.add(map);
            }
            Map map=new HashMap ();
            map.put("new",news);
            map.put("user",userService.getUser(news.getUserId()));
            return msg.success().add("news",map).add("comments",arraylist);
        }
        return msg.fail("没有发现此信息");
    }

    @RequestMapping(path = {"add/addComment"}, method = {RequestMethod.POST})
    public msg addComment(@RequestParam("newsId") int newsId,
                             @RequestParam("content") String content) {

            Comment comment = new Comment();
            comment.setUserId(hostHolder.getUser().getId());
            comment.setContent(content);
            comment.setEntityType(EntityType.ENTITY_NEWS);
            comment.setEntityId(newsId);
            comment.setCreatedDate(new Date());
            comment.setStatus(0);
            commentService.addComment(comment);

            // 更新评论数量，以后用异步实现
            int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
            newsService.updateCommentCount(comment.getEntityId(), count);


        return msg.success();
    }



    @RequestMapping(path = {"/uploadImage"},method = {RequestMethod.POST})
    public msg uploadImage(@RequestParam("file")MultipartFile file){
        //String fileUrl = newsService.saveImage(file);
        String fileUrl = qiniuService.saveImage(file);
        if (fileUrl==null){
            return msg.fail("上传图片失败");
        }
        return msg.success("上传成功").add("fileUrl",fileUrl);
    }


    @RequestMapping(path = {"/image"}, method = {RequestMethod.GET})
    public void getImage(@RequestParam("name") String imageName,
                         HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");
            StreamUtils.copy(qiniuService.getImage(imageName), response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    @RequestMapping(path = {"/user/addNews"}, method = {RequestMethod.POST})
    public msg addNews(@RequestParam("image") String image,
                          @RequestParam("title") String title,
                          @RequestParam("link") String link) {
        try {
            News news = new News();
            news.setCreatedDate(new Date());
            news.setTitle(title);
            news.setImage(image);
            news.setLink(link);
            if (hostHolder.getUser() != null) {
                news.setUserId(hostHolder.getUser().getId());
            } else {
                // 设置一个匿名用户
                news.setUserId(3);
            }
            newsService.addNews(news);
            return msg.success();
        } catch (Exception e) {
            e.getMessage();
            return msg.fail("发布失败");
        }
    }

 */

}
