package com.gt.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class IotException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String msg;

    private final int code;

    private Object data;

    public IotException(String msg) {
        super(msg);
        this.msg = msg;
        this.code = 0;
    }

    public IotException(int code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public IotException(String msg,String resurt) {
        super(msg);
        this.msg = msg;
        this.data=resurt;
        this.code = 0;
    }
}
