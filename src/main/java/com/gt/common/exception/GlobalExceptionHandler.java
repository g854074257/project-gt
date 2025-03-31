package com.gt.common.exception;

import com.gt.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**-------- 自定义定异常处理方法 --------**/
    @ExceptionHandler(IotException.class)
    @ResponseBody
    public Result error(IotException e) {
        log.error("全局自定义异常", e);
        return new Result(e.getCode(), e.getMsg());
    }
}
