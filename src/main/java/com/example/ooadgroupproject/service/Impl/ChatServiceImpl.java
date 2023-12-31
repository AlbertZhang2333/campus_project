package com.example.ooadgroupproject.service.Impl;

import com.example.ooadgroupproject.controller.WebsocketServer;
import com.example.ooadgroupproject.service.ChatService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {
    private final Logger logger=Logger.getLogger(ChatServiceImpl.class);
    private final String ADMIN_MAIL="3077161150@qq.com";
    @Override
    public void sendMessageToUser(String message, String toUserMAil){
        try{
            WebsocketServer.sendInfo(message,toUserMAil);
        } catch (Exception e) {
            logger.error(e);
        }
    }
    @Override
    public void sendMessageToAdmin(String message){
        try{
            WebsocketServer.sendInfo(message,ADMIN_MAIL);
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
