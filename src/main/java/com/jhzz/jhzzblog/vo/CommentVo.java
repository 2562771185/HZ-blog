package com.jhzz.jhzzblog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/27
 * \* Time: 0:43
 * \* Description:
 * \
 */
@Data
public class CommentVo {
    /**
     *
     评论id
     */
    @JsonSerialize(using = ToStringSerializer.class) //防止前端解析造成精度损失
    private Long id;
    /**
     * 作者
     */
    private UserVo author;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 子评论
     */
    private List<CommentVo> childrens;
    /**
     * 评论时间
     */
    private String createDate;
    /**
     * 评论楼层
     */
    private Integer level;
    /**
     * 评论者
     */
    private UserVo toUser;
}
