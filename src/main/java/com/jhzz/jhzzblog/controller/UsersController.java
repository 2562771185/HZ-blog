package com.jhzz.jhzzblog.controller;

import com.jhzz.jhzzblog.service.SysUserService;
import com.jhzz.jhzzblog.vo.commons.CommonResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/26
 * \* Time: 0:20
 * \* Description:
 * \
 */
@RestController
@CrossOrigin
@RequestMapping("jhzzblog/users")
public class UsersController {
    @Resource
    private SysUserService userService;

    @RequestMapping("currentUser")
    public CommonResult currentUser(@RequestHeader("Authorization") String token){
            return userService.findUserByToken(token);
    }
}
