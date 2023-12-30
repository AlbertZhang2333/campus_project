package com.example.ooadgroupproject.Config;

import com.example.ooadgroupproject.common.LoginUserInfo;
import com.example.ooadgroupproject.entity.Account;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.util.Map;

@Configuration
public class WebsocketConfig extends ServerEndpointConfig.Configurator {
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
    @Override
    public void modifyHandshake(ServerEndpointConfig config,
                                HandshakeRequest request, HandshakeResponse response) {
        super.modifyHandshake(config, request, response);
    }
}
