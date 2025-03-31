package com.gt.common.utils;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * 对象转换全局工具类
 *
 * @author liurui
 * @date 2020/5/20
 */
public class ObjectMapperUtil {
    private ObjectMapperUtil() {
    }

    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();

        //当找不到对应的序列化器时 忽略此字段

        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //使Jackson JSON支持Unicode编码非ASCII字符
        SimpleModule module = new SimpleModule();
        module.addSerializer(String.class, new StringUnicodeSerializer());
        MAPPER.registerModule(module);

        //设置null值不参与序列化(字段不被显示)
//        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);


        //所有字段可见
        MAPPER.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //序列化的时候序列对象，bean的属性设置
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //反序列化的时候如果多了其它属性，不抛出异常
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //如果是空对象的时候，不抛异常
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //取消时间的转换格式，默认是时间戳，同时需设置时间格式
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

        MAPPER.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString("");
            }
        });
    }

    public static ObjectMapper getObjectMapper() {
        return MAPPER;
    }
}
