package com.gt.test.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gt.common.handler.ListTypeHandler;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.util.List;

@Data
@TableName(value = "users", autoResultMap = true)
@MappedTypes(List.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class User {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    
    @TableField("name")
    private String name;
    
    @TableField("age")
    private Integer age;
    
    @TableField("phone")
    private String phone;
    
    @TableField(value = "tags", typeHandler = ListTypeHandler.class, jdbcType = JdbcType.ARRAY)
    private List<String> tags;
}
