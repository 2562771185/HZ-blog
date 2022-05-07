package com.jhzz.jhzzblog.controller;

import com.jhzz.jhzzblog.service.ITagService;
import com.jhzz.jhzzblog.vo.TagVo;
import com.jhzz.jhzzblog.vo.commons.CommonResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jhzz
 * @since 2022-04-25
 */
@RestController
@RequestMapping("tags")
@CrossOrigin
public class TagController {
    @Resource
    ITagService iTagService;

    //    http://localhost:8888/jhzzblog/tags/hot
    @RequestMapping("hot")
    public CommonResult TagHots() {
        int limit = 6;
        return iTagService.hots(limit);
    }

    @RequestMapping
    public CommonResult tags() {
        List<TagVo> list = iTagService.findTags();
        //转化为vo对象
        return CommonResult.success(list);
    }

    //    http://localhost:8888/jhzzblog/tags/detail
    @RequestMapping("detail")
    public CommonResult TagsDetail() {
        //转化为vo对象
        return iTagService.findAllDetail();
    }
//    detail/{id}
@RequestMapping("detail/{id}")
public CommonResult TagsDetailById(@PathVariable("id")Long tagId) {
    //转化为vo对象
    return iTagService.findTagDetailById(tagId);
}


}


