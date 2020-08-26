package com.zzx.configuration;

import org.springframework.context.annotation.Bean;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @ClassName WebSocketConfig
 * @Deacription:
 * @Author zzx
 * @Date 2020/3/13 17:54
 **/
@Component
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
