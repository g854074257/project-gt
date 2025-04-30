package com.gt.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gt.common.aop.SpSecretAnnotation;
import com.gt.test.mapper.TestMapper;
import com.gt.test.model.User;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    /**
     * 使用MyBatis-Plus内置方法获取所有用户
     */
    @GetMapping(value = "/users")
    public List<User> getUsers() {
        return testMapper.selectList(null);
    }
    
    /**
     * 分页查询用户
     */
    @GetMapping(value = "/users/page")
    public IPage<User> getUsersByPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        // 创建分页对象
        Page<User> page = new Page<>(pageNum, pageSize);
        // 创建查询条件（这里为空，表示查询所有）
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        // 执行分页查询
        return testMapper.selectPage(page, queryWrapper);
    }
}
