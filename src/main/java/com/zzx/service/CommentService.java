package com.zzx.service;

import com.zzx.beans.Comment;
import com.zzx.dao.CommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName CommentService
 * @Deacription:
 * @Author zzx
 * @Date 2020/1/17 13:55
 **/

@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;

    public List<Comment> getCommentsByEntity(int entityId,int entityType){
        return commentDao.selectByEntity(entityId, entityType,0);
    }

    public int addComment(Comment comment){
        return commentDao.addComment(comment);
    }

    public int getCommentCount(int entityId,int entityType){
        return commentDao.getCommentCount(entityId, entityType);
    }

}
