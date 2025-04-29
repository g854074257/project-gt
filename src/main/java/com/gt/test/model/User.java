package com.gt.test.model;

import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.util.List;

@Data
@MappedTypes(List.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class User {

    private Long id;
    private String name;
    private Integer age;
    private String phone;
    private List<String> tags;
}
