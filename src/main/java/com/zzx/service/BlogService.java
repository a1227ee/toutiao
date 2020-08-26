package com.zzx.service;

import com.zzx.beans.Blog;
import com.zzx.beans.Tag;
import com.zzx.beans.User;
import com.zzx.dao.BlogDao;
import com.zzx.dao.TagDao;
import com.zzx.dao.UserDao;
import com.zzx.util.JedisAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName BlogService
 * @Deacription:
 * @Author zzx
 * @Date 2020/1/19 16:36
 **/
@Service
public class BlogService {
    @Autowired
    BlogDao blogDao;
    @Autowired
    TagDao tagDao;
    @Autowired
    UserDao userDao;
    @Autowired
    JedisAdapter jedisAdapter;



    @Transactional
    public int addBlog(Blog blog, String[] tagIds) {
        int addBlog = blogDao.addBlog(blog);
        for (String tagid : tagIds) {
            tagDao.addTag(blog.getId(), Integer.valueOf(tagid));
        }
        return addBlog;
    }

    @Transactional
    public Blog getBlog(int id) {
        Blog blog = blogDao.selectById(id);
        if (blog==null){
            return null;
        }
        List<Tag> tags = tagDao.selectById(id);
        if (tags!=null){
            blog.setTags(tags);
        }
        return blog;
    }

    //增加访问量(通过Ip)
    @Transactional
    public Blog getBlog(int id,String userIp) {
        Blog blog = blogDao.selectById(id);
        if (blog==null){
            return null;
        }
        if (!jedisAdapter.sismember("BLOGID:"+id,userIp)){
            jedisAdapter.sadd("BLOGID:"+id,userIp);
            setViews(id);
            blog.setViews(blog.getViews()+1);
        }
        List<Tag> tags = tagDao.selectById(id);
        if (tags!=null){
            blog.setTags(tags);
        }

        return blog;
    }
    /**
     * @description:通过删除,添加更改信息.
     * @author: zzx
     * @date: 2020/1/20  14:58
     * @param
     * @return:
     */
    @Transactional
    public void updateBlog(Blog blog, String[] tagIds) {
        blogDao.deleteBlog(blog.getId(),blog.getUserId());
        blogDao.addUpdateBlog(blog);
        tagDao.deleteById(blog.getId());
        for (String tagid : tagIds) {
            tagDao.addTag(blog.getId(), Integer.valueOf(tagid));
        }
    }

    public List<Blog> getBlogByUser(int id) {
        return blogDao.getByUserId(id);
    }

    public List<Blog> getIndex() {

        return blogDao.findBlogByStatus();
    }

    public int getCount() {
        return blogDao.selectCountBlog();
    }

    public User getUserById(int id) {
        return userDao.selectById( id);
    }

    public synchronized void setViews(int id) {
        blogDao.updateViews(id);
    }



    public void deleteBlog(int blogId, int userId) {
        blogDao.deleteBlog(blogId,userId);
    }
}
