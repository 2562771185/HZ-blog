package com.jhzz.jhzzblog.handler;

import com.alibaba.fastjson.JSON;
import com.jhzz.jhzzblog.entity.SysUser;
import com.jhzz.jhzzblog.service.LoginService;
import com.jhzz.jhzzblog.utils.UserThreadLocal;
import com.jhzz.jhzzblog.vo.commons.CommonResult;
import com.jhzz.jhzzblog.vo.commons.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/26
 * \* Time: 14:14
 * \* Description:
 * \
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    private LoginService loginService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*
         * 在执行controller方法（handler）之前执行
         * 1、需要判断请求的接口路径是否为 HandlerMethod（controller方法）
         * 2、判断token 是否为空 空---》未登录
         * 3、如果token不为空 -- 进行登录验证 loginService checkToken
         * 4、如果认证成功 放行
         */
        if (!(handler instanceof HandlerMethod)){
            //可能是资源请求 RequestResourceHandler
            return true;
        }
        String token = request.getHeader("Authorization");
        log.info("============================= request start ========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}",token);
        log.info("============================= request end   ========================");

        if (StringUtils.isBlank(token)) {
            //如果token为空
            CommonResult result = CommonResult.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null){
            //如果sysUser为空
            CommonResult result = CommonResult.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        //验证成功 放行

        //希望在controller中直接获取用户信息
//        如何获取？ redis
        //放入线程
        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //如果不删除 ThreadLocal中用完的信息 会造成内存泄漏的风险
        UserThreadLocal.remove();
    }
}
