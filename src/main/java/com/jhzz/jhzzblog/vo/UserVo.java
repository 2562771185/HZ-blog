package com.jhzz.jhzzblog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/27
 * \* Time: 0:44
 * \* Description:
 * \
 */
@Data
public class UserVo {
    private String nickname;

    private String avatar;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
}
