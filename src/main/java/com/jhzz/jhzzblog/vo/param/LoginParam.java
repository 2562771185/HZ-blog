package com.jhzz.jhzzblog.vo.param;

import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/25
 * \* Time: 23:06
 * \* Description:
 * \
 */
@Data
public class LoginParam {
    private String account;
    private String password;
    private String nickname;
}
