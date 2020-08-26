package com.zzx.controller;

import com.github.pagehelper.PageHelper;
import com.zzx.beans.*;
import com.zzx.service.*;
import com.zzx.util.RequestIpUtil;
import org.commonmark.parser.Parser;
import org.commonmark.node.Node;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.rmi.MarshalledObject;
import java.util.*;

/**
 * @ClassName BlogController
 * @Deacription:
 * @Author zzx
 * @Date 2020/1/19 16:43
 **/

@RestController
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class BlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    TagService tagService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    TypeService typeService;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;


    @RequestMapping(path = {"/deleteBlog/{blogId}"},method = {RequestMethod.POST})
    public msg deleteBlog(@PathVariable("blogId") int blogId){
        User user = hostHolder.getUser();
        if (user==null){
            return msg.fail("请登录");
        }
        blogService.deleteBlog(blogId,user.getId());
        return msg.success();
    }

    @RequestMapping(path = {"/addBlog"},method = {RequestMethod.POST})
    public msg addBlog(@RequestParam("content")String content,@RequestParam("flag")String flag
            ,@RequestParam("title")String title,@RequestParam("typeId")int typeId,
             @RequestParam("published")int published,@RequestParam("firstPicture")String firstPicture,@RequestParam("status")int status,@RequestParam("tagIds") String tagIds){


        User user = hostHolder.getUser();
        if (user==null){
            return msg.fail("请登录，后再提交");
        }

        Blog blog = new Blog();
        blog.setUserId(user.getId());
        blog.setContent(content);
        blog.setFlag(flag);
        blog.setPublished(published);
        blog.setFirstPicture(firstPicture);
        blog.setTitle(title);
        blog.setTypeId(typeId);
        blog.setStatus(status);
        blog.setViews(0l);
        Date date = new Date();
        blog.setCreateDate(date);
        blog.setUpdateDate(date);
        String[] strings = tagIds.split(",");
        blogService.addBlog(blog,strings);
        return msg.success().add("blog",blog);
    }



    @RequestMapping(path = {"/getBlogs/{id}"},method = {RequestMethod.POST})
    public msg getBlog(@PathVariable("id") int id ){
        User user = hostHolder.getUser();
        if (user==null){
            return msg.fail("请返回页面查看");
        }
        Blog blog = blogService.getBlog(id);
        if (blog==null){
            return msg.fail("博客内容不存在");
        }
        List<Tag> tags = blog.getTags();
        String StringTypes="";

        if(blog.getUserId()!=user.getId()){
            return msg.fail("访问有误");
        }

        for (Tag tag:tags){
            StringTypes+=String.valueOf(tag.getId())+",";
        }

        String substring = StringTypes.substring(0, StringTypes.length()-1);

        return msg.success().add("blog",blog).add("StringTypes",substring);
    }

    @RequestMapping(path = {"/readBlog/{id}"},method = {RequestMethod.POST})
    public msg readBlog(@PathVariable("id") int id, HttpServletRequest request){
        String userIp = RequestIpUtil.getIp(request);
        Blog blog = blogService.getBlog(id,userIp);
        if (blog==null){
            return msg.fail();
        }
        if (blog.getStatus()==0||blog.getPublished()==0){
            return msg.fail("访问错误");
        }
        String typeName = typeService.getTypeByid(blog.getTypeId());

      //  Parser parser = Parser.builder().build();
      //  Node document = parser.parse(blog.getContent());
      //   HtmlRenderer renderer = HtmlRenderer.builder().build();
      //  String renderString = renderer.render(document);  // <h1>My name is <em>huhx</em></h1>
      //  blog.setContent(renderString);

        List arraylist=new ArrayList();

        List<Comment> comments = commentService.getCommentsByEntity(id, EntityType.ENTITY_NEWS);
        for (Comment comment:comments){
            Map map=new HashMap ();
            map.put("comment",comment);
            User user=userService.getUser(comment.getUserId());
            user.setPassword("*******");
            user.setName("*******");
            user.setSalt("*******");
            map.put("user",user);
            arraylist.add(map);
        }



        User user = blogService.getUserById(blog.getUserId());
        return msg.success().add("blog",blog).add("typeName",typeName).add("user",user).add("comments",arraylist)  ;
    }



    @RequestMapping(path = {"/getTagType"},method = {RequestMethod.POST})
    public msg getTag(){
        List tags = tagService.getTag();
        List<Type> types = typeService.getTypes();


        return msg.success().add("tags",tags).add("types",types) ;
    }



    @RequestMapping(path = {"/updateBlog"},method = {RequestMethod.POST})
    public msg updateblog(@RequestParam("blogId")int blogId,@RequestParam("content")String content,@RequestParam("flag")String flag
            ,@RequestParam("title")String title,@RequestParam("typeId")int typeId,
                          @RequestParam("published")int published,@RequestParam("firstPicture")String firstPicture,@RequestParam("status")int status,@RequestParam("tagIds") String tagIds){

        User user = hostHolder.getUser();
        if (user==null){
            return msg.fail("请登录，后再提交");
        }
        Blog blogUser = blogService.getBlog(blogId);
        if (user.getId()!=blogUser.getUserId()){
            return msg.fail();
        }

        Blog blog = new Blog();
        blog.setUserId(user.getId());
        blog.setContent(content);
        blog.setFlag(flag);
        blog.setPublished(published);
        blog.setViews(blogUser.getViews());
        blog.setFirstPicture(firstPicture);
        blog.setTitle(title);
        blog.setTypeId(typeId);
        blog.setStatus(status);
        Date date = new Date();
        blog.setCreateDate(date);
        blog.setUpdateDate(date);
        String[] strings = tagIds.split(",");
        blog.setId((long) blogId);
        blogService.updateBlog(blog,strings);

        return msg.success() ;
    }



    @RequestMapping(path = {"/getUserBlog"},method = {RequestMethod.POST})
    public msg getUserBlog(){
        User user = hostHolder.getUser();
        List<Blog> blogs = blogService.getBlogByUser(user.getId());
        List<Type> types = typeService.getTypes();
        return msg.success().add("blogs",blogs).add("type",types);
    }


    @RequestMapping(path = {"/getBlogs/{page}"},method = {RequestMethod.GET})
    public msg getBlogs(@PathVariable("page") int page){

        int pn=10;
        if ( page<=1){
            page=1;
        }
        PageHelper.offsetPage((page-1)*pn,10);
        List<Blog> blogs = blogService.getIndex();

        for (Blog blog:blogs){
            blog.setContent(blog.getContent().trim().replace("#","").substring(0,blog.getContent().trim().replace("#","").length()>=50?50:blog.getContent().replace("#","").trim().length())+".......");
        }

        return msg.success().add("blogs",blogs);


    }

    @RequestMapping(path = {"/getTypeTag"},method = {RequestMethod.GET})
    public msg zzxx(){
        List<Tag> tags = tagService.getTagSum();
        List<Type> types = typeService.getTypeSum();
        int count = blogService.getCount();
        return msg.success().add("tags",tags).add("types",types).add("count",count);
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
        return msg.success();
    }









}
