package com.zzx.service;

import com.zzx.beans.Message;
import com.zzx.dao.MessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName MessageService
 * @Deacription:
 * @Author zzx
 * @Date 2020/1/17 17:34
 **/
@Service
public class MessageService {
    @Autowired
    private MessageDao messageDAO;

    public int addMessage(Message message) {
        return messageDAO.addMessage(message);
    }

    public List<Message> getConversationList(int userId, int offset, int limit) {
        // conversation的总条数存在id里
        return messageDAO.getConversationList(userId, offset, limit);
    }

    public List<Message> getConversationDetail(String conversationId, int offset, int limit,int userId) {
        // conversation的总条数存在id里
        return messageDAO.getConversationDetail(conversationId, offset, limit,userId);
    }

    public int getUnreadCount(int userId, String conversationId) {
        return messageDAO.getConversationUnReadCount(userId, conversationId);
    }
}
