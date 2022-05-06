package com.jhzz.jhzzblog.service;

import com.jhzz.jhzzblog.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jhzz.jhzzblog.entity.dos.Archives;
import com.jhzz.jhzzblog.vo.commons.CommonResult;
import com.jhzz.jhzzblog.vo.param.ArticleParam;
import com.jhzz.jhzzblog.vo.param.PageParams;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jhzz
 * @since 2022-04-24
 */
public interface IArticleService extends IService<Article> {

    /**
     *返回热门文章：浏览量高的
     * @return 返回热门文章：浏览量高的
     * @param limit
     */
    List<Article> hotArticles(int limit);

    List<Article> newArticles(int limit);

    List<Archives> listArticls();

    /**
     * 根据id查询文章详情所需的信息 三表数据
     * @param id
     * @return
     */
    CommonResult findArticleById(Long id);

    /**
     * 发布文章
     * @param articleParam
     * @return
     */
    CommonResult publish(ArticleParam articleParam);

    CommonResult listArticle(PageParams pageParams);

    CommonResult searchArticle(String search);
}
