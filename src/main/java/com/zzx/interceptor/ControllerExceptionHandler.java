package com.zzx.interceptor;

import com.zzx.beans.msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    @ResponseBody

    @ExceptionHandler(RuntimeException.class)
    public msg exceptionHander(HttpServletRequest request, Exception e)  {
        logger.error("RequstURL:{},Exception:{}",request.getRequestURL(),e);
        return msg.fail("系统错误");

    }
}
