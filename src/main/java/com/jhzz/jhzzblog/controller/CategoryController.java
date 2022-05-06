package com.jhzz.jhzzblog.controller;

import com.jhzz.jhzzblog.mapper.CategoryMapper;
import com.jhzz.jhzzblog.service.ICategoryService;
import com.jhzz.jhzzblog.vo.CategoryVo;
import com.jhzz.jhzzblog.vo.commons.CommonResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jhzz
 * @since 2022-04-26
 */
@RestController
@RequestMapping("jhzzblog/categorys")
public class CategoryController {
    @Resource
    private ICategoryService categoryService;

    @RequestMapping
    public CommonResult categorys() {
        return categoryService.getAll();
    }

    //    http://localhost:8888/jhzzblog/categorys/detail
    @RequestMapping("detail")
    public CommonResult categoryDetail() {
        return categoryService.findAllDetail();
    }

    //    http://localhost:8888/jhzzblog/categorys/detail/1
    @RequestMapping("detail/{id}")
    public CommonResult categoryDetailById(@PathVariable("id")Long id) {
        CategoryVo categoryVo = categoryService.findCategoryById(id);
        return CommonResult.success(categoryVo);
    }


}

