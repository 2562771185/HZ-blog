package com.jhzz.jhzzblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jhzz.jhzzblog.entity.Tag;
import com.jhzz.jhzzblog.mapper.TagMapper;
import com.jhzz.jhzzblog.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jhzz.jhzzblog.vo.TagVo;
import com.jhzz.jhzzblog.vo.commons.CommonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jhzz
 * @since 2022-04-25
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {
    @Resource
    private TagMapper tagMapper;

    public TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        //拷贝属性
        BeanUtils.copyProperties(tag, tagVo);
        return tagVo;
    }
    public List<TagVo> copyList(List<Tag> tags){
        ArrayList<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tags) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        //mp 无法多表查询
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    @Override
    public CommonResult hots(int limit) {
        //1 标签所拥有最多文章数量 为 热门标签
//        2.查询 tag_id 分组 计数 大到小 排列 取limit个
        List<Long> hotTagIds = tagMapper.findHotTagIds(limit);
        if (CollectionUtils.isEmpty(hotTagIds)){
            //如果id数组为空 直接返回一个空数组
            return CommonResult.success(Collections.emptyList());
        }

//        需求的是tagId 和tagName  Tag对象
//
        List<Tag> tags = tagMapper.findTagsByIds(hotTagIds);

        return CommonResult.success(tags);
    }

    /**
     * 获取全部tag 并转化为vo对象
     *
     * @return
     */
    @Override
    public List<TagVo> findTags() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getTagName);
        List<Tag> tags = tagMapper.selectList(queryWrapper);
        List<TagVo> tagVoList = copyList(tags);
        return tagVoList;
    }

    /**
     * 查询所有的标签
     *
     * @return
     */
    @Override
    public CommonResult findAllDetail() {
        List<Tag> tags = tagMapper.selectList(null);
        return CommonResult.success(copyList(tags));
    }

    /**
     * 根据标签查询：标签文章列表
     *
     * @param tagId
     * @return
     */
    @Override
    public CommonResult findTagDetailById(Long tagId) {
        Tag tag = tagMapper.selectById(tagId);
        return CommonResult.success(copy(tag));
    }
}
