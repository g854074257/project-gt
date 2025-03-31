package com.gt.common.result;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 返回对象<br/>
 *
 * @author wanggh
 * @since 2019-12-16
 */
@Data
public final class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -2688668464917547230L;

    public Result() {
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回时间戳
     */
    private Long timestamp;

    private T data;

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }
}
