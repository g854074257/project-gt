package com.gt.common.config;

import com.taobao.tair.TairManager;
import com.taobao.tair.comm.TairClient;
import com.taobao.tair.comm.TairClientFactory;
import com.taobao.tair.etc.TairClientException;
import com.taobao.tair.impl.DefaultTairManager;
import com.taobao.tair.packet.BasePacket;
import com.taobao.tair.packet.PacketStreamer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TairConfig {

    @Value("${tair.servers}")
    private String servers;
    @Value("${tair.namespace}")
    private String namespace;
    @Value("${tair.password}")
    private String password;

    @Bean
    public TairClient tairClient() throws TairClientException {
        TairClientFactory tairClientFactory = new TairClientFactory();
        TairClient tairClient = tairClientFactory.get(servers, 60000, new PacketStreamer() {
            @Override
            public BasePacket decodePacket(int i, byte[] bytes) {
                return null;
            }
        });
        return tairClient;
    }
}
