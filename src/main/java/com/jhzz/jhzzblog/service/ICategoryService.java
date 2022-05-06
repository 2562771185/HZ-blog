package com.jhzz.jhzzblog.service;

import com.jhzz.jhzzblog.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jhzz.jhzzblog.vo.CategoryVo;
import com.jhzz.jhzzblog.vo.commons.CommonResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jhzz
 * @since 2022-04-26
 */

public interface ICategoryService extends IService<Category> {

    CategoryVo findCategoryById(Long categoryId);

    /**
     * 获取全部文章分类
     * @return
     */
    CommonResult getAll();

    /**
     * 查询所有标签
     * @return
     */
    CommonResult findAllDetail();

    /**
     * 根据id查询
     * @param id
     * @return
     */
    CommonResult findAllDetailById(Long id);
}
