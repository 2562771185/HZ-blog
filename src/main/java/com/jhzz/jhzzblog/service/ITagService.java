package com.jhzz.jhzzblog.service;

import com.jhzz.jhzzblog.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jhzz.jhzzblog.vo.TagVo;
import com.jhzz.jhzzblog.vo.commons.CommonResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jhzz
 * @since 2022-04-25
 */
public interface ITagService extends IService<Tag> {
    /**
     *根据文章id查询对应的标签列表
     * @param articleId
     * @return
     */
    List<TagVo> findTagsByArticleId(Long articleId);


    CommonResult hots(int limit);

    /**
     * 获取全部tag 并转化为vo对象
     * @return
     */
    List<TagVo> findTags();

    /**
     * 查询所有的标签
     * @return
     */
    CommonResult findAllDetail();

    /**
     * 根据标签查询：标签文章列表
     * @param tagId
     * @return
     */
    CommonResult findTagDetailById(Long tagId);
}
