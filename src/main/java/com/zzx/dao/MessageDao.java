package com.zzx.dao;

import com.zzx.beans.Message;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName MessageDAO
 * @Deacription:
 * @Author zzx
 * @Date 2020/1/17 17:33
 **/
@Mapper
@Repository
public interface MessageDao {
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " from_id, to_id, content, has_read, conversation_id, created_date ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{fromId},#{toId},#{content},#{hasRead},#{conversationId},#{createdDate})"})
    int addMessage(Message message);

    @Select({"select ", INSERT_FIELDS, " ,count(id) as id from ( select * from ", TABLE_NAME, " where   to_id=#{userId} order by id desc) tt group by conversation_id order by created_date desc limit #{offset},#{limit}"})
    List<Message> getConversationList(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from ", TABLE_NAME, " where has_read = 0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getConversationUnReadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

    @Select({"select count(id) from ", TABLE_NAME, " where has_read = 0 and to_id=#{userId}"})
    int getConversationTotalCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where to_id=#{userId} and conversation_id=#{conversationId} order by id desc limit #{offset},#{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId, @Param("offset") int offset, @Param("limit") int limit,@Param("userId") int userId);
}
