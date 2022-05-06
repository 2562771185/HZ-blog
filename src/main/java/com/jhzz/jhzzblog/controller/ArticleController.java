package com.jhzz.jhzzblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jhzz.jhzzblog.common.aop.LogAnnotation;
import com.jhzz.jhzzblog.common.cache.Cache;
import com.jhzz.jhzzblog.entity.Article;
import com.jhzz.jhzzblog.entity.SysUser;
import com.jhzz.jhzzblog.entity.dos.Archives;
import com.jhzz.jhzzblog.mapper.ArticleMapper;
import com.jhzz.jhzzblog.service.IArticleService;
import com.jhzz.jhzzblog.service.ITagService;
import com.jhzz.jhzzblog.service.SysUserService;
import com.jhzz.jhzzblog.vo.ArticleVo;
import com.jhzz.jhzzblog.vo.UserVo;
import com.jhzz.jhzzblog.vo.commons.CommonResult;
import com.jhzz.jhzzblog.vo.param.ArticleParam;
import com.jhzz.jhzzblog.vo.param.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.management.relation.RelationNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jhzz
 * @since 2022-04-24
 */
@RestController
@RequestMapping("/jhzzblog/articles")
/**
 * 跨域
 */
@CrossOrigin
public class ArticleController {

    @Resource
    private IArticleService articleService;
    @Resource
    private ITagService tagService;
    @Resource
    private SysUserService sysUserService;

    private PageParams pageParams = new PageParams();

    @PostMapping("/{id}")
    public CommonResult editArticle(@PathVariable("id") Long id) {
        return articleService.findArticleById(id);
    }

    @RequestMapping("/getAll")
    public CommonResult getAll() {
        List<Article> list = articleService.list();
        return new CommonResult(200, true, "查询成功", list);
    }

    /**
     * 分页查询
     *
     * @return
     */
    @RequestMapping
    @LogAnnotation(module = "文章", operation = "获取文章列表")
//    @Cache(expire = 5 * 60 * 1000, name = "listArticle")
    public CommonResult getPage(@RequestBody PageParams pageParams) {
        return articleService.listArticle(pageParams);
    }

    @RequestMapping("/hot")
    @Cache(expire = 5 * 60 * 1000, name = "hot_article")
    public CommonResult hotArticle() {
        int limit = 5;
        List<Article> articles = articleService.hotArticles(limit);

        return CommonResult.success(copyList(articles, false, false));

    }

    //    http://localhost:8888/jhzzblog/articles/new
    //最新文章 接口
    @RequestMapping("/new")
    public CommonResult newArticle() {
        //方法一：自己写sql
//        int limit = 5;
//        List<Article> articles = articleService.newArticles(limit);
//        return CommonResult.success(copyList(articles, false, false));
        // 方法2：mybatis plus
        int limit = 5;
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        //按照时间降序
        wrapper.orderByDesc(Article::getCreateDate);
        //需要查询的字段
        wrapper.select(Article::getId, Article::getTitle);
//        需要在最后拼接的sql语句
        wrapper.last("limit " + limit);
        List<Article> articleList = articleService.list(wrapper);
        return CommonResult.success(copyList(articleList, false, false));

    }

    /**
     * http://localhost:8888/jhzzblog/articles/listArchives
     * 文章归档接口
     */
    @RequestMapping("/listArchives")
    public CommonResult listArticles() {
        List<Archives> articles = articleService.listArticls();
        return CommonResult.success(articles);
    }

    /**
     * 根据id返回文章
     *
     * @param id
     * @return
     */
    @RequestMapping("/view/{id}")
    @LogAnnotation(module = "查看文章", operation = "获取文章详情")
//    @Cache(expire = 5 * 60 * 1000, name = "view_article") //一分钟的缓存
    public CommonResult viewArticle(@PathVariable("id") Long id) {
        return articleService.findArticleById(id);
    }


    /**
     * 发布文章 接口url：/publish
     *
     * @param articleParam
     * @return
     */
    @RequestMapping("publish")
    public CommonResult publish(@RequestBody ArticleParam articleParam) {
        return articleService.publish(articleParam);
    }

    @RequestMapping("search")
    public CommonResult search(@RequestBody
                                      ArticleParam articleParam) {
       String search = articleParam.getSearch();
       return articleService.searchArticle(search);
    }

    /**
     * @param list     ArticleVo对象
     * @param isTag    是否需要标签信息
     * @param isAuthor 是否需要作者信息
     * @return
     */
    private List<ArticleVo> copyList(List<Article> list, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : list) {
            articleVoList.add(copy(article, isTag, isAuthor));
        }
        return articleVoList;
    }

    /**
     * @param article  文章对象
     * @param isTag    是否需要标签信息
     * @param isAuthor 是否需要作者信息
     * @return
     */
    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setCreateDate(
                new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm:ss"));
        //并不是所有的接口都需要 标签信息和作者信息
        if (isTag) {
            Long id = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(id));
        }
        if (isAuthor) {
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(copy(sysUserService.findUserById(authorId)));
        }
        return articleVo;
    }

    private UserVo copy(SysUser user) {
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }


}
