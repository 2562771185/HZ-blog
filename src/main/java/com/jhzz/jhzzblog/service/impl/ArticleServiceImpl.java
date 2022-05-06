package com.jhzz.jhzzblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jhzz.jhzzblog.entity.*;
import com.jhzz.jhzzblog.entity.dos.Archives;
import com.jhzz.jhzzblog.mapper.ArticleBodyMapper;
import com.jhzz.jhzzblog.mapper.ArticleMapper;
import com.jhzz.jhzzblog.mapper.ArticleTagMapper;
import com.jhzz.jhzzblog.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jhzz.jhzzblog.utils.UserThreadLocal;
import com.jhzz.jhzzblog.vo.ArticleBodyVo;
import com.jhzz.jhzzblog.vo.ArticleVo;
import com.jhzz.jhzzblog.vo.TagVo;
import com.jhzz.jhzzblog.vo.UserVo;
import com.jhzz.jhzzblog.vo.commons.ArticleMessage;
import com.jhzz.jhzzblog.vo.commons.CommonResult;
import com.jhzz.jhzzblog.vo.param.ArticleParam;
import com.jhzz.jhzzblog.vo.param.PageParams;
import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jhzz
 * @since 2022-04-24
 */
@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private ITagService tagService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private ArticleBodyMapper articleBodyMapper;
    @Resource
    private ICategoryService categoryService;
    @Resource
    private ArticleTagMapper articleTagMapper;
//    @Resource
//    private RocketMQTemplate rocketMQTemplate;

    @Override
    public List<Article> hotArticles(int limit) {
        return articleMapper.getHotArticles(limit);
    }

    @Override
    public List<Article> newArticles(int limit) {
        return articleMapper.newArticles(limit);
    }

    @Override
    public List<Archives> listArticls() {

        List<Archives> articleList = articleMapper.listArticles();
        return articleList;
    }

    @Autowired
    private ThreadService threadService;

    @Override
    public CommonResult findArticleById(Long id) {
        /**
         *1、根据id查询 文章信息
         * 2、根据文章的bodyId 和categoryId 去关联查询 对应信息
         */
        Article article = articleMapper.selectById(id);
        ArticleVo articleVo = copy(article, true, true, true, true);
        //查看完文章 应新增阅读数 引发的问题：
//        查看文章后，本应该直接返回数据了。这个时候做了一个更新操作 更新操作需要加锁阻塞其他读操作 性能降低
//        更新 增加了此次接口的耗时 如果一旦更新操作出了问题--不能影响查看文章的操作
//          开一个线程池
        threadService.updateArticleViewCount(articleMapper, article);
        return CommonResult.success(articleVo);
    }

    /**
     * 发布文章
     *
     * @param articleParam
     * @return
     */
    @Override
    @Transactional
    public CommonResult publish(ArticleParam articleParam) {
        //此接口 要加入登录拦截器
        //获取已登录的用户
        SysUser sysUser = UserThreadLocal.get();
        /**
         * 1、发布文章，构建article对象
         * 2、作者id：当前登录的用户
         * 3、标签信息：要将标签加入到关联表当中
         *
         */
        //是否为编辑
        boolean isEdit = false;
        Article article = new Article();
        /**
         * 如果传入了id 说明是更新文章
         */
        if (articleParam.getId() != null){
            article.setId(articleParam.getId());
            article.setSummary(articleParam.getSummary());
            article.setTitle(articleParam.getTitle());
            article.setCategoryId(articleParam.getCategory().getId());
            articleMapper.updateById(article);
            isEdit = true;
        }else {
            article.setAuthorId(sysUser.getId());
            article.setWeight(Article.Article_Common);
            article.setViewCounts(0);
            article.setTitle(articleParam.getTitle());
            article.setCreateDate(System.currentTimeMillis());
            article.setSummary(articleParam.getSummary());
            article.setCommentCounts(0);
            article.setCategoryId(articleParam.getCategory().getId());
        //插入之后会生成一个文章id
        articleMapper.insert(article);
        }


        //tag
        List<TagVo> tags = articleParam.getTags();
        if (tags != null) {
            for (TagVo tag : tags) {
                Long id = article.getId();
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(tag.getId());
                articleTag.setArticleId(id);
                articleTagMapper.insert(articleTag);
            }
        }
        //body内容
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        //插入一个ArticleBody 产生id
        articleBodyMapper.insert(articleBody);
        //设置article表中的bodyId
        article.setBodyId(articleBody.getId());
//        更新这个文章 之前只是插入了一个空文章
        articleMapper.updateById(article);
        HashMap<String, String> map = new HashMap<>();
        //转成string防止精度丢失
        map.put("id", article.getId().toString());
        //如果是编辑文章后发布
        if (isEdit){
            //发一条消息给rocketmq 更新缓存
            ArticleMessage articleMessage = new ArticleMessage();
            articleMessage.setArticleId(article.getId());
            //发送消息和数据
//            暂时不用缓存策略
//            rocketMQTemplate.convertAndSend("blog-update-article",articleMessage);

        }
        return CommonResult.success(map);

        //返回值的第二种方式
//        ArticleVo articleVo1 = new ArticleVo();
//        articleVo1.setId(article.getId());
//        return CommonResult.success(articleVo1);
    }

    @Override
    public CommonResult listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getSize());
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        //是否叫上分类id和标签id
        if (pageParams.getCategoryId() != null) {
            wrapper.eq(Article::getCategoryId, pageParams.getCategoryId());
        }
        //如果有tagId说明要根据标签id分类显示 文章
        List<Long> articleIdList = new ArrayList<>();
        if (pageParams.getTagId() != null) {
            log.info("tagid---{}", pageParams.getTagId());
            LambdaQueryWrapper<ArticleTag> articleTagWrapper = new LambdaQueryWrapper<>();
            articleTagWrapper.eq(ArticleTag::getTagId, pageParams.getTagId());
            List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagWrapper);
            log.info("根据id查询到的tags----------------------" + articleTags.size());
            if (articleTags.size() > 0) {
                log.info("+articleTags.size() > 0");
                for (ArticleTag articleTag : articleTags) {
                    articleIdList.add(articleTag.getArticleId());
                }
            }
            log.info("articleIdList----------------------" + articleIdList.size());

            if (articleIdList.size() > 0) {
                log.info("articleIdList.size() > 0");

                wrapper.in(Article::getId, articleIdList);
            }
        }
        //根据是否置顶进行降序
        wrapper.orderByDesc(Article::getWeight);
        //根据创建时间进行升序 两个条件也可以写在一起
        wrapper.orderByDesc(Article::getCreateDate);

        Page<Article> articlePage = articleMapper.selectPage(page, wrapper);
        log.info("articlePage-------------------------------{}", articlePage.getSize());
        List<Article> articleList = articlePage.getRecords();
        List<ArticleVo> articleVoList = copyList(articleList, true, true);
        //最终返回Vo对象给前端
        return CommonResult.success(articleVoList);
    }

    @Override
    public CommonResult searchArticle(String search) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.like(Article::getTitle, search);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        return CommonResult.success(copyList(articleList,false,false));
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

    private List<ArticleVo> copyList(List<Article> list, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
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

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
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
            SysUser user = sysUserService.findUserById(authorId);

            articleVo.setAuthor(copy(user));
        }
        if (isBody) {
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (isCategory) {
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }

        return articleVo;
    }

    private UserVo copy(SysUser user) {
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }

    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }
}
