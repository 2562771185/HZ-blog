package com.jhzz.jhzzblog.controller;

import com.jhzz.jhzzblog.service.LoginService;
import com.jhzz.jhzzblog.vo.commons.CommonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/26
 * \* Time: 0:56
 * \* Description:
 * \
 */
@RestController
@CrossOrigin
@RequestMapping("/jhzzblog")
public class LogoutController {
    @Resource
    private LoginService loginService;

//   http://localhost:8888/jhzzblog/logout
    @RequestMapping("/logout")
    public CommonResult logout(@RequestHeader("Authorization")String token){
        return loginService.logout(token);
    }
}
