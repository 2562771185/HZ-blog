package com.jhzz.jhzzblog.service;

import com.jhzz.jhzzblog.entity.SysUser;
import com.jhzz.jhzzblog.vo.commons.CommonResult;
import com.jhzz.jhzzblog.vo.param.LoginParam;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/25
 * \* Time: 23:05
 * \* Description:
 * \
 */
public interface LoginService {
    /**
     * 登录功能
     * @param loginParam 账号和密码
     * @return
     */
    CommonResult login(LoginParam loginParam);

    /**
     * 检查token是否存在
     * @param token
     * @return
     */
    SysUser checkToken(String token);

    /**
     * 退出登录 清除redis中的token
     * @param token
     * @return
     */
    CommonResult logout(String token);

    /**
     * 注册功能
     * @param loginParam
     * @return
     */
    CommonResult register(LoginParam loginParam);
}
