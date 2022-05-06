package com.jhzz.jhzzblog.vo.param;

import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/25
 * \* Time: 10:56
 * \* Description:
 * \
 */
@Data
public class PageParams {
    private Integer page = 1;
    private Integer size = 10;
    private Long categoryId;
    private Long tagId ;
}
