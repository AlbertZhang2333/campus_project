package com.example.ooadgroupproject.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.ooadgroupproject.Config.WebsocketConfig;
import com.example.ooadgroupproject.IdentityLevel;
import com.example.ooadgroupproject.Utils.JwtUtils;
import com.example.ooadgroupproject.common.LoginUserInfo;
import com.example.ooadgroupproject.entity.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/ws/{passToken}",configurator = WebsocketConfig.class)
@Component
public class ChatComponent {
    private static int onlineCount=0;
    private static ConcurrentHashMap<String, ChatComponent> websocketMap =new ConcurrentHashMap<>();
    private Session session;
    private String userMail="";
    private static Logger logger=Logger.getLogger(ChatComponent.class);

    //连接成功后调用下列方法：
    @OnOpen
    public void onOpen(@PathParam("passToken")String passToken, Session session){
        if(passToken==null||StrUtil.isBlankOrUndefined(passToken)){
            logger.error("连接失败");
            return;
        }
        Jws<Claims> jws= JwtUtils.parseClaim(passToken);
        if(jws==null){
            logger.error("连接失败");
            return;
        }

        String userMail = (String) jws.getPayload().get("userMail");
        this.session=session;
        session.setMaxIdleTimeout(3600000);
        this.userMail=userMail;
        //接下来，存储连接
        websocketMap.put(userMail,this);
        logger.info(JSONUtil.toJsonStr(websocketMap));
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
        websocketMap.remove(userMail);
        logger.info(JSONUtil.toJsonStr(websocketMap));
    }
    @OnMessage
    public void onMessage(String message,Session session){
        logger.info("收到消息："+message);
        if(!message.isEmpty()&&message!=null){
            try {
                JSONObject jsonObject= JSONUtil.parseObj(message);
                jsonObject.put("fromUserMail",this.userMail);
                String toUserMail=jsonObject.getStr("toUserMail");
                websocketMap.get(toUserMail).sendMessage(JSONUtil.toJsonStr(jsonObject));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @OnError
    public void OnError(Session session,Throwable error){
        logger.error(this.userMail+"用户遇到了故障:"+error.getMessage());
    }

    private void sendMessage(String message){
        synchronized (this.session) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
    public static void sendInfo(String message,String userMail)throws Exception{
        Iterator entrys= websocketMap.entrySet().iterator();
        while(entrys.hasNext()){
            Map.Entry entry=(Map.Entry)entrys.next();
            if(entry.getKey().toString().equals(userMail)){
                websocketMap.get(entry.getKey()).sendMessage(message);
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
        ChatComponent.onlineCount++;
    }

    private static synchronized void subOnlineCount(){
        ChatComponent.onlineCount--;
    }
}
