package com.jhzz.jhzzblog.mapper;

import com.jhzz.jhzzblog.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author jhzz
 * @since 2022-04-25
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根据文章id查询对应的标签列表
     *
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(@Param("articleId")Long articleId);

    /**
     * 查询热门标签
     * @param limit
     * @return
     */
    List<Long>  findHotTagIds(Integer limit);

    /**
     * 根据tagId数组 查询到tag对象列表
     * @param hotTagIds
     * @return
     */
    List<Tag> findTagsByIds(List<Long> hotTagIds);
}
