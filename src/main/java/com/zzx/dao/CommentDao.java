package com.zzx.dao;

import com.zzx.beans.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName CommentDao
 * @Deacription:
 * @Author zzx
 * @Date 2020/1/17 13:39
 **/
@Mapper
@Repository
public interface CommentDao {
    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " user_id, content, created_date, entity_id, entity_type, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{content},#{createdDate},#{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where entity_type=#{entityType}  and entity_id=#{entityId} and status=#{status} order by id desc "})
    List<Comment> selectByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType,@Param("status") int status);

    @Select({"select count(id) from ", TABLE_NAME, " where entity_type=#{entityType} and entity_id=#{entityId}"})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);
}
