package com.jhzz.jhzzblog.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.jhzz.jhzzblog.entity.Article;
import com.jhzz.jhzzblog.mapper.ArticleBodyMapper;
import com.jhzz.jhzzblog.mapper.ArticleMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/26
 * \* Time: 22:17
 * \* Description:
 * \
 */

@Component
public class ThreadService {
    /**
     * 期望此操作在线程池中执行，不影响主线程
     * @param articleMapper
     * @param article
     */
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        int viewCount = article.getViewCounts();
        Article updateArticle = new Article();
        //阅读数加一
        updateArticle.setViewCounts(viewCount+1);
        LambdaUpdateWrapper<Article> wrapper = new LambdaUpdateWrapper<>();
        //根据id修改：传进来的article对象的id
        wrapper.eq(Article::getId, article.getId());
        //设置一个为了在多线程环境下能安全运行
        //判断现在的阅读数 跟 刚刚获取的阅读数是否一致 防止被其它线程修改
        wrapper.eq(Article::getViewCounts, viewCount);
        //执行更新操作
        //SQL: update article set view_count = 100 where view_count = 99 and id = 11
        articleMapper.update(updateArticle,wrapper);
        try {
            Thread.sleep(3000);
            System.out.println("---------------------阅读数更新完成-------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
