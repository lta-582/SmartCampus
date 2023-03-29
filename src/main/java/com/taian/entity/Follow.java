package com.taian.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Follow对象", description = "文章关注关系表")
public class Follow implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("关注目标的ID")
    private Integer followableId;

    @ApiModelProperty("关注时间")
    private Integer createTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getFollowableId() {
        return followableId;
    }

    public void setFollowableId(Integer followableId) {
        this.followableId = followableId;
    }
    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Follow{" +
            "userId=" + userId +
            ", followableId=" + followableId +
            ", createTime=" + createTime +
        "}";
    }
}
