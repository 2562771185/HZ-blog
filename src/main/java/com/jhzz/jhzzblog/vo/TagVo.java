package com.jhzz.jhzzblog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/25
 * \* Time: 11:26
 * \* Description:
 * \
 */
@Data
public class TagVo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String tagName;
    private String avatar;
}
