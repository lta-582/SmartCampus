package com.taian.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "Inbox对象", description = "私信表")
public class Inbox implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("私信ID")
    @TableId(value = "inbox_id", type = IdType.AUTO)
    private Integer inboxId;

    @ApiModelProperty("接收者ID")
    private Integer receiverId;

    @ApiModelProperty("发送者ID")
    private Integer senderId;

    @ApiModelProperty("私信内容")
    private String contentRendered;

    @ApiModelProperty("发送时间")
    private Integer createTime;

    @ApiModelProperty("阅读时间")
    private Integer readTime;

    public Integer getInboxId() {
        return inboxId;
    }

    public void setInboxId(Integer inboxId) {
        this.inboxId = inboxId;
    }
    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }
    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }
    public String getContentRendered() {
        return contentRendered;
    }

    public void setContentRendered(String contentRendered) {
        this.contentRendered = contentRendered;
    }
    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }
    public Integer getReadTime() {
        return readTime;
    }

    public void setReadTime(Integer readTime) {
        this.readTime = readTime;
    }

    @Override
    public String toString() {
        return "Inbox{" +
            "inboxId=" + inboxId +
            ", receiverId=" + receiverId +
            ", senderId=" + senderId +
            ", contentRendered=" + contentRendered +
            ", createTime=" + createTime +
            ", readTime=" + readTime +
        "}";
    }
}
