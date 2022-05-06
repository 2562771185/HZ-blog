package com.jhzz.jhzzblog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/26
 * \* Time: 0:41
 * \* Description:
 * \
 */
@Data
public class LoginUserVo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String account;
    private String nickname;
    private String avatar;
}
