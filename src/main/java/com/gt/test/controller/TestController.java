package com.gt.test.controller;

import com.gt.common.aop.SpSecretAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @SpSecretAnnotation
    @GetMapping(value = "/test")
    public void test() {
        log.info("test");
    }
}
