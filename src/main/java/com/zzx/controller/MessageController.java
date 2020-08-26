package com.zzx.controller;

import com.zzx.beans.HostHolder;
import com.zzx.beans.Message;
import com.zzx.beans.User;
import com.zzx.beans.msg;
import com.zzx.service.MessageService;
import com.zzx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @ClassName MessageController
 * @Deacription:
 * @Author zzx
 * @Date 2020/1/17 17:43
 **/
@RestController
public class MessageController {
    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public msg conversationDetail(  @RequestParam("conversationId") String conversationId) {
            List  messages = new ArrayList<>();
            int localUserId = hostHolder.getUser().getId();
            List<Message> conversationList = messageService.getConversationDetail(conversationId, 0, 10,localUserId);
            for (Message msg : conversationList) {
                Map map=new HashMap();
                map.put("message", msg);
                User user = userService.getUser(msg.getFromId());
                if (user == null) {
                    continue;
                }
                map.put("headUrl", user.getHeadUrl());
                map.put("userName", user.getName());
                messages.add(map);
            }


        return msg.success().add("messages",messages);
    }

    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public msg conversationList(Model model) {
        List  messages = new ArrayList<>();
            int localUserId = hostHolder.getUser().getId();
            List<Message> conversationList = messageService.getConversationList(localUserId, 0, 10);
            for (Message msg : conversationList) {
                Map map=new HashMap();
                map.put("conversation", msg);
                int targetId = msg.getFromId() == localUserId ? msg.getToId() : msg.getFromId();
                User user = userService.getUser(targetId);
                map.put("headUrl", user.getHeadUrl());
                map.put("userName", user.getName());
                map.put("targetId", targetId);
                map.put("totalCount", msg.getId());
                map.put("unreadCount", messageService.getUnreadCount(localUserId, msg.getConversationId()));
                messages.add(map);
            }
        return msg.success().add("messages",messages);
    }

    @RequestMapping(path = {"/msg/addMessage"}, method = {RequestMethod.GET, RequestMethod.POST})

    public String addMessage(@RequestParam("fromId") int fromId,
                             @RequestParam("toId") int toId,
                             @RequestParam("content") String content) {

        Message msg = new Message();
        msg.setContent(content);
        msg.setCreatedDate(new Date());
        msg.setToId(toId);
        msg.setFromId(fromId);
        msg.setConversationId(fromId < toId ? String.format("%d_%d", fromId, toId) :
                String.format("%d_%d", toId, fromId));
        messageService.addMessage(msg);
        return String.valueOf(msg.getId());
    }
}
