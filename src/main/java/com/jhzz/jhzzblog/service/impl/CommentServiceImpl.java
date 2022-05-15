package com.jhzz.jhzzblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jhzz.jhzzblog.entity.Article;
import com.jhzz.jhzzblog.entity.Comment;
import com.jhzz.jhzzblog.entity.SysUser;
import com.jhzz.jhzzblog.mapper.ArticleMapper;
import com.jhzz.jhzzblog.mapper.CommentMapper;
import com.jhzz.jhzzblog.service.CommentService;
import com.jhzz.jhzzblog.service.SysUserService;
import com.jhzz.jhzzblog.utils.UserThreadLocal;
import com.jhzz.jhzzblog.vo.CommentVo;
import com.jhzz.jhzzblog.vo.UserVo;
import com.jhzz.jhzzblog.vo.commons.CommonResult;
import com.jhzz.jhzzblog.vo.param.CommentParam;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author Huanzhi
* @description 针对表【ms_comment】的数据库操作Service实现
* @createDate 2022-04-27 00:22:27
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private RedisTemplate redisTemplate;
    @Override
    public CommonResult commentsByArticleId(Long articleId) {
        /**
         * 1、根据文章id 查询评论列表 从comment表中查询
         * 2、根据作者id查询作者信息
         * 3、判断：如果level = 1 要去查询它的子评论
         * 4、如果有子评论则根据子评论id进行查询（parent_id）
         */
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getArticleId, articleId);
        wrapper.orderByDesc(Comment::getCreateDate);
        wrapper.eq(Comment::getLevel, 1);
        List<Comment> comments = commentMapper.selectList(wrapper);
        List<CommentVo> commentVoList = copyList(comments);
        return CommonResult.success(commentVoList);
    }

    /**
     * 新增评论
     *
     * @param commentParam
     * @return
     */
    @Override
    public CommonResult comment(CommentParam commentParam) {
//        获取已登录的用户 redis中获取
        SysUser sysUser = UserThreadLocal.get();
//      redisTemplate.opsForValue().get("login-"+)
        Comment comment = new Comment();
        //article_id超出范围？？？
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
//        判断是一级评论还是二级评论
        if (parent == null || parent == 0) {
            comment.setLevel("1");
        }else{
            comment.setLevel("2");
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.commentMapper.insert(comment);
        //增加评论数
        Long articleId = commentParam.getArticleId();
        Article article = articleMapper.selectById(articleId);
        article.setCommentCounts(article.getCommentCounts() + 1);
        articleMapper.updateById(article);
        CommentVo commentVo = copy(comment);
        return CommonResult.success(commentVo);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        //作者信息
        Long authorId = comment.getAuthorId();
        UserVo userVo = sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);
        //子评论
        String level = comment.getLevel();
        if ("1".equals(level)){
            Long id = comment.getId();
            List<CommentVo> commentVoList = findCommentVoByParentId(id);
            //设置子评论
            commentVo.setChildrens(commentVoList);
        }
//      to_user  给谁评论  如果不是第一层说明说别人的子评论
        if (!"1".equals(level)){
            Long toUid = comment.getToUid();
            UserVo userVoById = sysUserService.findUserVoById(toUid);
            commentVo.setToUser(userVoById);
        }
        return commentVo;
    }

    private List<CommentVo> findCommentVoByParentId(Long parentId) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getParentId,parentId);
        wrapper.eq(Comment::getLevel,"2");
        return copyList(commentMapper.selectList(wrapper));

    }
}




