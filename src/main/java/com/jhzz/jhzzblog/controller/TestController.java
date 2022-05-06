package com.jhzz.jhzzblog.controller;

import com.jhzz.jhzzblog.entity.SysUser;
import com.jhzz.jhzzblog.utils.UserThreadLocal;
import com.jhzz.jhzzblog.vo.commons.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/26
 * \* Time: 14:42
 * \* Description:
 * \
 */
@RestController
@RequestMapping("test")
@Slf4j
public class TestController {

    @RequestMapping
    public CommonResult testTest(){
        SysUser sysUser = UserThreadLocal.get();
        log.info("--------------sysUser:{}",sysUser);
        return CommonResult.success(null);
    }
}
