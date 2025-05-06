package com.gt.test.controller;


import com.gt.common.aop.SpSecretAnnotation;
import com.taobao.tair.DataEntry;
import com.taobao.tair.Result;
import com.taobao.tair.TairManager;
import com.taobao.tair.comm.TairClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TairController {

    @Autowired
    private TairManager tairManager;

    @Autowired
    private TairClient tairClient;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @SpSecretAnnotation
    @GetMapping(value = "/tair")
    public void tair() {
        log.info("tair");
        tairManager.put(0, "key", "value");

        Result<DataEntry> key = tairManager.get(0, "key");


        System.out.println(key.toString());
        DataEntry value = key.getValue();
        System.out.println(value.toString());


    }

    @SpSecretAnnotation
    @GetMapping(value = "/redis")
    public void redis() {
        log.info("redis");
        redisTemplate.opsForValue().set("key", "value");
        Object key = redisTemplate.opsForValue().get("key");
        System.out.println(key.toString());
    }
}
