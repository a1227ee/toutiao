package com.zzx.util;

import com.zzx.beans.HostHolder;
import com.zzx.beans.User;
import com.zzx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName MyWebSocket
 * @Deacription:
 * @Author zzx
 * @Date 2020/3/13 17:55
 **/
@Component
@ServerEndpoint(value = "/websocket"  )
public class MyWebSocket {
    // 通过类似GET请求方式传递参数的方法（服务端采用第二种方法"WebSocketHandler"实现）
//    websocket = new WebSocket("ws://127.0.0.1:18080/testWebsocket?id=23&name=Lebron");
    /**
     * 在线人数
     */
    public static AtomicInteger onlineNumber = new AtomicInteger(0);

    /**
     * 所有的对象，每次连接建立，都会将我们自己定义的MyWebSocket存放到List中，
     */
    public static List<MyWebSocket> webSockets = new CopyOnWriteArrayList<MyWebSocket>();

    /**
     * 会话，与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 每个会话的用户
     */
    private User user;


    private HostHolder hostHolder  ;

    /**
     * 建立连接
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        hostHolder = SpringUtil.getBean(HostHolder.class);
        User user = hostHolder.getUser();
        System.out.println("==============================+"+user+"======================");
        onlineNumber.incrementAndGet();
        if (user == null ) {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        for (MyWebSocket myWebSocket : webSockets) {
            if (user.getName().equals(myWebSocket.user.getName())) {
                try {
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return;
            }
        }
        this.session = session;
        this.user =  user;
        webSockets.add(this);
        System.out.println("有新连接加入！ 当前在线人数" + onlineNumber.get());
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        onlineNumber.decrementAndGet();
        webSockets.remove(this);

        System.out.println("有连接关闭！ 当前在线人数" + onlineNumber.get());
    }

    /**
     * 收到客户端的消息
     *
     * @param message 消息
     *
     */
    @OnMessage
    public void onMessage(String message) {

        pushMessage( message);
    }

    /**
     * 发送消息
     *
     * @param message 消息
     */
    public void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消息推送
     *
     * @param message
     * @param uuid    uuid为空则推送全部人员
     */
    public   void pushMessage(String message, String uuid) {
        String s="<li><div class='answerHead'><img src="+user.getHeadUrl()+"></div>" +
                "<div class='answers'>"+message+"</div>" +
                "</li>";
        if (uuid == null || "".equals(uuid)) {
            for (MyWebSocket myWebSocket : webSockets) {
                myWebSocket.sendMessage(s);
            }
        } else {
            for (MyWebSocket myWebSocket : webSockets) {
                if (uuid.equals(myWebSocket.user)) {
                    myWebSocket.sendMessage(message);
                }
            }
        }

    }

    public   void pushMessage(String message) {

            for (MyWebSocket myWebSocket : webSockets) {
                if (myWebSocket==this){
                    continue;
                }
                myWebSocket.sendMessage(message);
            }

    }

}
