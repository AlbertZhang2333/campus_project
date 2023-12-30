package com.example.ooadgroupproject.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.ooadgroupproject.Config.WebsocketConfig;
import com.example.ooadgroupproject.IdentityLevel;
import com.example.ooadgroupproject.common.LoginUserInfo;
import com.example.ooadgroupproject.entity.Account;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/ws/{userMail}",configurator = WebsocketConfig.class)
@Component
public class WebsocketServer {
    private static int onlineCount=0;
    private static ConcurrentHashMap<String,WebsocketServer>webSocketMap=new ConcurrentHashMap<>();
    private Session session;
    private String userMail="";
    private static Logger logger=Logger.getLogger(WebsocketServer.class);

    //连接成功后调用下列方法：
    @OnOpen
    public void onOpen(Session session){
        Account account= LoginUserInfo.getAccount();
        //游客无权限做此操作
        if(account.getIdentity()== IdentityLevel.VISITOR){
            return;
        }
        String userMail=account.getUserMail();
        this.session=session;
        session.setMaxIdleTimeout(3600000);
        this.userMail=userMail;
        //接下来，存储连接
        webSocketMap.put(userMail,this);
        logger.info(JSONUtil.toJsonStr(webSocketMap));
        //加锁了，所以是线程安全的
        addOnlineCount();
        logger.info("当前连接人数为"+getOnlineCount());
        sendMessage(JSONUtil.toJsonStr("连接成功"));
    }

    @OnClose
    public void onClose(){
        //减锁
        subOnlineCount();
        logger.info("当前连接人数为"+getOnlineCount());
        //删除连接
        webSocketMap.remove(userMail);
        logger.info(JSONUtil.toJsonStr(webSocketMap));
    }
    @OnMessage
    public void onMessage(String message,Session session){
        logger.info("收到消息："+message);
        if(!message.isEmpty()&&message!=null){
            try {
                JSONObject jsonObject= JSONUtil.parseObj(message);
                jsonObject.put("formUserMail",this.userMail);
                String toUserMail=jsonObject.getStr("toUserMail");
                webSocketMap.get(toUserMail).sendMessage(JSONUtil.toJsonStr(jsonObject));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @OnError
    public void OnError(Session session,Throwable error){
        logger.error(this.userMail+"用户遇到了故障:"+error.getMessage());
    }

    public void sendMessage(String message){
        synchronized (this.session) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
    public static void sendInfo(String message,String userMail)throws Exception{
        Iterator entrys=webSocketMap.entrySet().iterator();
        while(entrys.hasNext()){
            Map.Entry entry=(Map.Entry)entrys.next();
            if(entry.getKey().toString().equals(userMail)){
                webSocketMap.get(entry.getKey()).sendMessage(message);
                logger.info("向"+userMail+"发送消息："+message);
                return;
            }
        }
        throw new Exception(userMail+"用户不在线");
    }

    private static synchronized int getOnlineCount(){
        return onlineCount;
    }
    private static synchronized void addOnlineCount(){
        WebsocketServer.onlineCount++;
    }

    private static synchronized void subOnlineCount(){
        WebsocketServer.onlineCount--;
    }
}
