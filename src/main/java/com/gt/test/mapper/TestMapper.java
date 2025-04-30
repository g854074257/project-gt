package com.gt.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gt.test.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper extends BaseMapper<User> {

    /**
     * 自定义方法：获取所有用户
     */
    List<User> getAllUsers();
}
