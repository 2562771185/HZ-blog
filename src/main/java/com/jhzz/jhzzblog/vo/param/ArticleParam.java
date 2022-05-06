package com.jhzz.jhzzblog.vo.param;

import com.jhzz.jhzzblog.vo.CategoryVo;
import com.jhzz.jhzzblog.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/27
 * \* Time: 11:37
 * \* Description:
 * \
 */
@Data
public class ArticleParam {
    /**
     * 文章id：自动生成分布式id
     */
    private Long id;
    /**
     * 文章主题
     */
    private ArticleBodyParam body;
    /**
     * 文章分类
     */
    private CategoryVo category;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 标签
     */
    private List<TagVo> tags;
    /**
     * 标题
     */
    private String title;

    private String search;
}
