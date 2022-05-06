package com.jhzz.jhzzblog.controller;

import com.jhzz.jhzzblog.service.LoginService;
import com.jhzz.jhzzblog.vo.commons.CommonResult;
import com.jhzz.jhzzblog.vo.param.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/26
 * \* Time: 11:36
 * \* Description:
 * \ 注册控制器
 */
@RestController
@CrossOrigin
@RequestMapping("jhzzblog")
public class RegisterController {
    @Autowired
    private LoginService loginService;

    @RequestMapping("register")
    public CommonResult register(@RequestBody LoginParam loginParam){
        //sso 单点登录 后期如果吧登录注册功能 提取出来（当做单独的服务 可以独立提过接口）
        return loginService.register(loginParam);
    }

}
