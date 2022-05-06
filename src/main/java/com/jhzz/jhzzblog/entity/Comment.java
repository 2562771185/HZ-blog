package com.jhzz.jhzzblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;

/**
 * 
 * @TableName ms_comment
 */
@TableName(value ="ms_comment")
public class Comment implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private String content;

    /**
     * 
     */
    private Long createDate;

    /**
     * 
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long articleId;

    /**
     * 
     */
    private Long authorId;

    /**
     * 
     */
    private Long parentId;

    /**
     * 
     */
    private Long toUid;

    /**
     * 
     */
    private String level;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 
     */
    public Long getCreateDate() {
        return createDate;
    }

    /**
     * 
     */
    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    /**
     * 
     */
    public Long getArticleId() {
        return articleId;
    }

    /**
     * 
     */
    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    /**
     * 
     */
    public Long getAuthorId() {
        return authorId;
    }

    /**
     * 
     */
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    /**
     * 
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 
     */
    public Long getToUid() {
        return toUid;
    }

    /**
     * 
     */
    public void setToUid(Long toUid) {
        this.toUid = toUid;
    }

    /**
     * 
     */
    public String getLevel() {
        return level;
    }

    /**
     * 
     */
    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Comment other = (Comment) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
            && (this.getArticleId() == null ? other.getArticleId() == null : this.getArticleId().equals(other.getArticleId()))
            && (this.getAuthorId() == null ? other.getAuthorId() == null : this.getAuthorId().equals(other.getAuthorId()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getToUid() == null ? other.getToUid() == null : this.getToUid().equals(other.getToUid()))
            && (this.getLevel() == null ? other.getLevel() == null : this.getLevel().equals(other.getLevel()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getArticleId() == null) ? 0 : getArticleId().hashCode());
        result = prime * result + ((getAuthorId() == null) ? 0 : getAuthorId().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getToUid() == null) ? 0 : getToUid().hashCode());
        result = prime * result + ((getLevel() == null) ? 0 : getLevel().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", content=").append(content);
        sb.append(", createDate=").append(createDate);
        sb.append(", articleId=").append(articleId);
        sb.append(", authorId=").append(authorId);
        sb.append(", parentId=").append(parentId);
        sb.append(", toUid=").append(toUid);
        sb.append(", level=").append(level);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}