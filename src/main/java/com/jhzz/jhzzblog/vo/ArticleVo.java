package com.jhzz.jhzzblog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/25
 * \* Time: 11:21
 * \* Description:
 * \
 */
@Data
public class ArticleVo {

    //一定要记得加 要不然 会出现精度损失
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Integer commentCounts;
    private String createDate;
    private String title;
    private String summary;
    private Integer viewCounts;
    private Integer weight;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long authorId;
    //修改：为author对象包含头像
    private UserVo author;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long bodyId;
    private Integer categoryId;
    private ArticleBodyVo body;
    private List<TagVo> tags;
    //    private List<CategoryVo> categories;
    private CategoryVo category;

}
