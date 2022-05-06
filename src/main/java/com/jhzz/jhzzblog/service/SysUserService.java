package com.jhzz.jhzzblog.service;

import com.jhzz.jhzzblog.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jhzz.jhzzblog.vo.UserVo;
import com.jhzz.jhzzblog.vo.commons.CommonResult;

/**
* @author Huanzhi
* @description 针对表【ms_sys_user】的数据库操作Service
* @createDate 2022-04-25 11:43:45
*/
public interface SysUserService extends IService<SysUser> {
    SysUser findUserById(Long id);
    /**
     * 根据用户名和密码查询
     * @param account 用用户名
     * @param password 密码
     * @return 返回一个SysUser对象
     */
    SysUser findUser(String account, String password);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    CommonResult findUserByToken(String token);

    /**
     * 根据account查询用户是否存在
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 根据id 返回Vo对象
     * @param id
     * @return
     */
    UserVo findUserVoById(Long id);
}
