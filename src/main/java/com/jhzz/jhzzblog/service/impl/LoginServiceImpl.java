package com.jhzz.jhzzblog.service.impl;

import com.alibaba.fastjson.JSON;
import com.jhzz.jhzzblog.entity.SysUser;
import com.jhzz.jhzzblog.service.LoginService;
import com.jhzz.jhzzblog.service.SysUserService;
import com.jhzz.jhzzblog.utils.JWTUtils;
import com.jhzz.jhzzblog.vo.commons.CommonResult;
import com.jhzz.jhzzblog.vo.commons.ErrorCode;
import com.jhzz.jhzzblog.vo.param.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/25
 * \* Time: 23:09
 * \* Description:
 * \
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Resource
    @Lazy
    private SysUserService userService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    //加密盐
    private static final String slat = "345323Jzhz!@$*(";

    @Override
    public CommonResult login(LoginParam loginParam) {
        /**
         * 1、检查参数是否合法
         * 2、根据用户名和密码查询表
         * 3、如果不存在，登录失败
         * 4、如果用户存在。使用jwt生成token 返回给前端
         * 5、token放入redis  token：user信息，设置过期时间
         * （先认证token字符串是否合法。再确认redis认证是否存在）
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        //参数不能为空
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return CommonResult.fail(ErrorCode.PARAMS_ERROR.getCode(), "login: " + ErrorCode.PARAMS_ERROR.getMsg());
        }
        System.out.println("加密之前的密码----- " + password);
        //加密密码
        password = DigestUtils.md5Hex(password + slat);
        System.out.println("加密之后的密码----- " + password);
        //因为数据库中是加密之后的密码 所以要传入加密之后的密码查询
        SysUser sysUser = userService.findUser(account, password);
        if (sysUser == null) {
            return CommonResult.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), "login: " + ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        //如果用户存在 则构造token
        String token = JWTUtils.createToken(sysUser.getId());
        //将token放入redis中缓存 并设置过期时间为1天
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        System.out.println("sysUser = " + sysUser);
        return CommonResult.success(token);
    }

    /**
     * 检查redis中token是否存在
     *
     * @param token
     * @return
     */
    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null) {
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)) {
            return null;
        }
        //解析json数据为SysUser对象
        return JSON.parseObject(userJson, SysUser.class);
    }

    /**
     * 退出登录 清除redis中的token
     *
     * @param token
     * @return
     */
    @Override
    public CommonResult logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return CommonResult.success(null, "退出成功");
    }

    /**
     * 注册功能
     *
     * @param loginParam
     * @return
     */
    @Override
    public CommonResult register(LoginParam loginParam) {
        /**
         * 注册流程
         * 1、判断参数是否合法
         * 2、判断账户是否存在 ，存在则返回失败
         * 3、不存在 则注册新用户
         * 4、生成token
         * 5、存入redis 并返回
         * 6、注意 ： 要加上事务 一旦中间出现问题 需要回滚
         */
//        1、判断参数是否合法
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)) {
            return CommonResult.fail(ErrorCode.PARAMS_ERROR.getCode(),"注册参数异常！！！");
        }
//        2、判断账户是否存在 ，存在则返回失败
        SysUser sysUser = userService.findUserByAccount(account);
        if (sysUser != null) {
            return CommonResult.fail(ErrorCode.ACCOUNT_EXIST.getCode(), "账户已存在！！");
        }
//        3、不存在 则注册新用户
        sysUser = new SysUser();
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+slat));
        sysUser.setNickname(nickname);
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setStatus("");
        //默认头像
        sysUser.setAvatar("/static/user/user_1.png");
        sysUser.setEmail("");
        sysUser.setSalt("");
        sysUser.setAdmin(true);
        sysUser.setDeleted(false);
        userService.save(sysUser);
//        4、生成token
//        5、存入redis 并返回
        String token = JWTUtils.createToken(sysUser.getId());
        //将token放入redis中缓存 并设置过期时间为1天
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return CommonResult.success(token);
    }


}
