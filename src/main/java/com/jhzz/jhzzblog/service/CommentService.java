package com.jhzz.jhzzblog.service;

import com.jhzz.jhzzblog.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jhzz.jhzzblog.vo.commons.CommonResult;
import com.jhzz.jhzzblog.vo.param.CommentParam;

/**
* @author Huanzhi
* @description 针对表【ms_comment】的数据库操作Service
* @createDate 2022-04-27 00:22:27
*/
public interface CommentService extends IService<Comment> {

    /**
     * 根据文章id查询评论列表
     * @param articleId
     * @return
     */
    CommonResult commentsByArticleId(Long articleId);

    /**
     * 新增评论
     * @param commentParam
     * @return
     */
    CommonResult comment(CommentParam commentParam);
}
