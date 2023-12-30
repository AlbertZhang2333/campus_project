package com.example.ooadgroupproject.service;

public interface ChatService {
    void sendMessageToUser(String message, String toUserMAil);

    void sendMessageToAdmin(String message);
}
