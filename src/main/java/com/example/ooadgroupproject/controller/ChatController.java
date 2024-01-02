package com.example.ooadgroupproject.controller;


import com.example.ooadgroupproject.service.ChatComponent;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/ChatController")
public class ChatController {
    private final Logger logger=Logger.getLogger(this.getClass());
    private final String ADMIN_MAIL="3077161150@qq.com";
    @Autowired
    private ChatComponent chatComponent;

    @PostMapping("/AdminSendMessageToUser")
    public void sendMessageToUser(String message, String toUserMAil){
        try{
            ChatComponent.sendInfo(message,toUserMAil);
        } catch (Exception e) {
            logger.error(e);
        }
    }
    @PostMapping("/userSendMessageToAdmin")
    public void sendMessageToAdmin(String message){
        try{
            ChatComponent.sendInfo(message,ADMIN_MAIL);
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
