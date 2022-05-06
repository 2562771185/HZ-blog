package com.jhzz.jhzzblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName ms_sys_log
 */
@TableName(value ="ms_sys_log")
public class SysLog implements Serializable {
    /**
     * 
     */

    private Long id;

    /**
     * 
     */
    private Long createDate;

    /**
     * 
     */
    private String ip;

    /**
     * 
     */
    private String method;

    /**
     * 
     */
    private String module;

    /**
     * 
     */
    private String nickname;

    /**
     * 
     */
    private String operation;

    /**
     * 
     */
    private String params;

    /**
     * 
     */
    private Long time;

    /**
     * 
     */
    private Long userid;

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
    public String getIp() {
        return ip;
    }

    /**
     * 
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 
     */
    public String getMethod() {
        return method;
    }

    /**
     * 
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * 
     */
    public String getModule() {
        return module;
    }

    /**
     * 
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * 
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 
     */
    public String getOperation() {
        return operation;
    }

    /**
     * 
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * 
     */
    public String getParams() {
        return params;
    }

    /**
     * 
     */
    public void setParams(String params) {
        this.params = params;
    }

    /**
     * 
     */
    public Long getTime() {
        return time;
    }

    /**
     * 
     */
    public void setTime(Long time) {
        this.time = time;
    }

    /**
     * 
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * 
     */
    public void setUserid(Long userid) {
        this.userid = userid;
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
        SysLog other = (SysLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getMethod() == null ? other.getMethod() == null : this.getMethod().equals(other.getMethod()))
            && (this.getModule() == null ? other.getModule() == null : this.getModule().equals(other.getModule()))
            && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
            && (this.getOperation() == null ? other.getOperation() == null : this.getOperation().equals(other.getOperation()))
            && (this.getParams() == null ? other.getParams() == null : this.getParams().equals(other.getParams()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getMethod() == null) ? 0 : getMethod().hashCode());
        result = prime * result + ((getModule() == null) ? 0 : getModule().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getOperation() == null) ? 0 : getOperation().hashCode());
        result = prime * result + ((getParams() == null) ? 0 : getParams().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", createDate=").append(createDate);
        sb.append(", ip=").append(ip);
        sb.append(", method=").append(method);
        sb.append(", module=").append(module);
        sb.append(", nickname=").append(nickname);
        sb.append(", operation=").append(operation);
        sb.append(", params=").append(params);
        sb.append(", time=").append(time);
        sb.append(", userid=").append(userid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}