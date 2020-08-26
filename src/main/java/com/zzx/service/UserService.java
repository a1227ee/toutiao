package com.zzx.service;

import com.zzx.beans.LoginTicket;
import com.zzx.beans.User;
import com.zzx.dao.LoginTicketDao;

import com.zzx.dao.UserDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.*;

/**
 * @ClassName UserService
 * @Deacription:
 * @Author zzx
 * @Date 2020/1/9 11:01
 **/
@Service

public class UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    private LoginTicketDao loginTicketDao;


    public synchronized   Map<String,Object> register(String username , String password){

        Map<String,Object>map=new HashMap<String,Object>();
        if (StringUtils.isBlank(username)){
            map.put("msgname","用戶名不能為空");
            return map;
        }

        if (StringUtils.isBlank(password)){
            map.put("msgpwd","密碼不能為空不能為空");
            return map;
        }

        User user= userDao.selectByName(username);


        if (user!=null){
            map.put("msg","用戶名已經註冊");
            return map;
        }

        user=new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setPassword(DigestUtils.md5DigestAsHex((password+user.getSalt()).getBytes()));
        user.setNick(UUID.randomUUID().toString().substring(0,5));
        userDao.addUser(user);
        //登录



        String ticket=addLoginTicket(user.getId());
        map.put("ticket",ticket);

        return map;
    }
    @Transactional
    public Map<String,Object> login(String username , String password){
        Map<String,Object>map=new HashMap<String,Object>();
        if (StringUtils.isBlank(username)){
            map.put("msgname","用戶名不能為空");
            return map;
        }
        if (StringUtils.isBlank(password)){
            map.put("msgpwd","密碼不能為空不能為空");
            return map;
        }

        User user= userDao.selectByName(username);

        if (user==null){
            map.put("msg","用戶名不存在");
            return map;
        }

        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex((password+user.getSalt()).getBytes()))){
            map.put("msgpwd","密码不正确");
            return  map;
        }
        //ticket
        String ticket=addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    private String addLoginTicket(int userId){
        LoginTicket ticket=new LoginTicket();
        ticket.setUserId(userId);
        Date date =new Date();
        date.setTime(date.getTime()+1000*3600*24*5);
        ticket.setStatus(0);
        ticket.setExpired(date);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDao.addTicket(ticket);
        return ticket.getTicket();

    }
    public void logout(String ticket){
        loginTicketDao.updateStatus(ticket,1);
    }

    public User getUser(int id){
        return userDao.selectById(id);
    }
}
