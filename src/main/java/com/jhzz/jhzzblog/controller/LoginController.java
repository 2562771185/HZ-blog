package com.jhzz.jhzzblog.controller;

import com.jhzz.jhzzblog.service.LoginService;
import com.jhzz.jhzzblog.vo.commons.CommonResult;
import com.jhzz.jhzzblog.vo.param.LoginParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/25
 * \* Time: 23:04
 * \* Description:
 * \
 */
@RestController
@CrossOrigin
@RequestMapping
public class LoginController {
    @Resource
    private LoginService loginService;

    @RequestMapping("login")
    public CommonResult login(@RequestBody LoginParam loginParam){
        return loginService.login(loginParam);
    }
}
