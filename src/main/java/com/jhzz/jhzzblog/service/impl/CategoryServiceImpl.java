package com.jhzz.jhzzblog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jhzz.jhzzblog.entity.Category;
import com.jhzz.jhzzblog.entity.Tag;
import com.jhzz.jhzzblog.mapper.CategoryMapper;
import com.jhzz.jhzzblog.mapper.TagMapper;
import com.jhzz.jhzzblog.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jhzz.jhzzblog.vo.CategoryVo;
import com.jhzz.jhzzblog.vo.commons.CommonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jhzz
 * @since 2022-04-26
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private TagMapper tagMapper;

    @Override
    public CategoryVo findCategoryById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }

    /**
     * 获取全部文章分类
     *
     * @return
     */
    @Override
    public CommonResult getAll() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Category::getId,Category::getCategoryName);
        List<Category> list = categoryMapper.selectList(queryWrapper);
        //转化为vo对象
        return CommonResult.success(copyList(list));
    }

    /**
     * 查询所有标签
     *
     * @return
     */
    @Override
    public CommonResult findAllDetail() {
        List<Category> categories = categoryMapper.selectList(null);
        //页面交互的对象
        return CommonResult.success(copyList(categories));
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public CommonResult findAllDetailById(Long id) {
        return null;
    }

    private List<CategoryVo> copyList(List<Category> list) {
        ArrayList<CategoryVo> categoryVos = new ArrayList<>();
        for (Category category : list) {
            categoryVos.add(copy(category));
        }
        return categoryVos;
    }

    private CategoryVo copy(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}
