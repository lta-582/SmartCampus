package com.taian.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Notification对象", description = "通知表")
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("通知ID")
    @TableId(value = "notification_id", type = IdType.AUTO)
    private Integer notificationId;

    @ApiModelProperty("接收者ID")
    private Integer receiverId;

    @ApiModelProperty("发送者ID")
    private Integer senderId;

    @ApiModelProperty("消息类型：comment vote")
    private String type;

    @ApiModelProperty("文章ID")
    private Integer articleId;

    @ApiModelProperty("评论ID")
    private Integer commentId;

    @ApiModelProperty("发送时间")
    private Integer createTime;

    @ApiModelProperty("阅读时间")
    private Integer readTime;

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
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
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }
    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
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
        return "Notification{" +
            "notificationId=" + notificationId +
            ", receiverId=" + receiverId +
            ", senderId=" + senderId +
            ", type=" + type +
            ", articleId=" + articleId +
            ", commentId=" + commentId +
            ", createTime=" + createTime +
            ", readTime=" + readTime +
        "}";
    }
}
