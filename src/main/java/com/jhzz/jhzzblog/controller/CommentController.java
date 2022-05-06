package com.jhzz.jhzzblog.controller;

import com.jhzz.jhzzblog.service.CommentService;
import com.jhzz.jhzzblog.vo.commons.CommonResult;
import com.jhzz.jhzzblog.vo.param.CommentParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/27
 * \* Time: 0:32
 * \* Description:
 * \
 */
@RestController
@RequestMapping("/jhzzblog/comments")
public class CommentController {
    @Resource
    private CommentService commentService;

    @RequestMapping(value = "/article/{id}")
    public CommonResult articleComment(@PathVariable("id") Long articleId){

        return commentService.commentsByArticleId(articleId);
    }
    //评论接口url：/comments/create/change
    @RequestMapping(value = "/create/change")
    public CommonResult addComment(@RequestBody CommentParam commentParam){
        return commentService.comment(commentParam);
    }

}
