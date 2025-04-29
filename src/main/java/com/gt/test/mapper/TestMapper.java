package com.gt.test.mapper;

import com.gt.test.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {

    List<User> getAllUsers();
}
