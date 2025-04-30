package com.gt.common.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.jdbc.PgArray;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 处理PostgreSQL text[]类型与Java List<String>之间的转换
 */
@MappedTypes(List.class)
@MappedJdbcTypes(JdbcType.ARRAY)
public class ListTypeHandler extends BaseTypeHandler<List<String>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            ps.setNull(i, java.sql.Types.ARRAY);
        } else {
            ps.setArray(i, ps.getConnection().createArrayOf("text", parameter.toArray()));
        }
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toList(rs.getArray(columnName));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toList(rs.getArray(columnIndex));
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toList(cs.getArray(columnIndex));
    }

    private List<String> toList(Array array) throws SQLException {
        if (array == null) {
            return null;
        }
        
        try {
            // 尝试直接获取数组
            Object[] objArray = (Object[]) array.getArray();
            return Arrays.stream(objArray)
                    .map(obj -> obj == null ? null : String.valueOf(obj))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // 如果直接获取失败，尝试使用PostgreSQL特定的方法
            if (array instanceof PgArray) {
                String arrayAsString = array.toString();
                // 移除大括号并分割为元素
                if (arrayAsString.startsWith("{") && arrayAsString.endsWith("}")) {
                    String content = arrayAsString.substring(1, arrayAsString.length() - 1);
                    return Arrays.stream(content.split(","))
                            .map(String::trim)
                            .collect(Collectors.toList());
                }
            }
            // 如果还是失败，返回null
            return null;
        }
    }
} 