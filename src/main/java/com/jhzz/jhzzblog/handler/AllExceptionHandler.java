package com.jhzz.jhzzblog.handler;

import com.jhzz.jhzzblog.vo.commons.CommonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/25
 * \* Time: 15:34
 * \* Description:
 * \
 */
//对加了@controller注解的方法进行拦截处理 AOP的实现
@ControllerAdvice
public class AllExceptionHandler {
//    进行异常处理，处理Exception.class 的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody //返回json数据
    public CommonResult doException(Exception e){
        e.printStackTrace();
        return CommonResult.fail(-999,"系统异常!!!!");
    }
}
