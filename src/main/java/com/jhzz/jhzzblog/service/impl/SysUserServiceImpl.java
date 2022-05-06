package com.jhzz.jhzzblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jhzz.jhzzblog.entity.SysUser;
import com.jhzz.jhzzblog.service.LoginService;
import com.jhzz.jhzzblog.service.SysUserService;
import com.jhzz.jhzzblog.mapper.SysUserMapper;
import com.jhzz.jhzzblog.utils.JWTUtils;
import com.jhzz.jhzzblog.vo.LoginUserVo;
import com.jhzz.jhzzblog.vo.UserVo;
import com.jhzz.jhzzblog.vo.commons.CommonResult;
import com.jhzz.jhzzblog.vo.commons.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.language.Nysiis;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Huanzhi
 * @description 针对表【ms_sys_user】的数据库操作Service实现
 * @createDate 2022-04-25 11:43:45
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
        implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private LoginService loginService;

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setAvatar("static/user/user_1.png");
            sysUser.setNickname("HZ—博客");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getAccount, account);
        wrapper.eq(SysUser::getPassword, password);
        wrapper.select(SysUser::getAccount, SysUser::getId, SysUser::getAvatar, SysUser::getNickname);
        wrapper.last("limit 1");
        return sysUserMapper.selectOne(wrapper);
    }

    @Override
    public CommonResult findUserByToken(String token) {
        /**
         * 1. token合法性检验：是否为空 - 解释是否成功 - redis是否存在
         * 2. 如果校验失败 返回错误
         * 3， 如果成功 返回对应结果 LoginUserVo
         */
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null) {
            return CommonResult.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAvatar(sysUser.getAvatar());

        return CommonResult.success(loginUserVo);
    }

    /**
     * 根据account查询用户是否存在
     *
     * @param account
     * @return
     */
    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getAccount, account);
        wrapper.last("limit 1");
        SysUser sysUser = sysUserMapper.selectOne(wrapper);
        return sysUser;
    }

    /**
     * 根据id 返回Vo对象
     *
     * @param id
     * @return
     */
    @Override
    public UserVo findUserVoById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setId(1L);
            sysUser.setAvatar("static/user/user_1.png");
            sysUser.setNickname("HZ—博客");
        }
        UserVo userVo = new UserVo();
//        userVo.setId(sysUser.getId());
//        userVo.setNickname(sysUser.getNickname());
//        userVo.setAvatar(sysUser.getAvatar());

        BeanUtils.copyProperties(sysUser, userVo);
        log.info("userVo对象：{}",userVo);
        return userVo;
    }


}




