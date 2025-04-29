package com.gt.test.controller;

import com.gt.common.aop.SpSecretAnnotation;
import com.gt.test.mapper.TestMapper;
import com.gt.test.model.User;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class TestController {

    @Resource
    private TestMapper testMapper;

    @SpSecretAnnotation
    @GetMapping(value = "/test")
    public List<User> test() {
        log.info("test");
        return testMapper.getAllUsers();
    }
}
