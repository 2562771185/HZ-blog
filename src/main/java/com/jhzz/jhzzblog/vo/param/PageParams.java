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
    private Integer page;
    private Integer pageSize;
    private Integer month;
//    private Integer year;
//    private String sort;
//    private String name;
    private Long categoryId;
    private Long tagId ;
}
