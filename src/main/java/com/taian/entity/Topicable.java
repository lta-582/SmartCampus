package com.taian.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "Topicable对象", description = "")
public class Topicable implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("话题ID")
    private Integer topicId;

    @ApiModelProperty("话题关系对应的ID")
    private Integer topicableId;

    @ApiModelProperty("话题关系对应的类型 question、article")
    private String topicableType;

    private Integer createTime;

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }
    public Integer getTopicableId() {
        return topicableId;
    }

    public void setTopicableId(Integer topicableId) {
        this.topicableId = topicableId;
    }
    public String getTopicableType() {
        return topicableType;
    }

    public void setTopicableType(String topicableType) {
        this.topicableType = topicableType;
    }
    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Topicable{" +
            "topicId=" + topicId +
            ", topicableId=" + topicableId +
            ", topicableType=" + topicableType +
            ", createTime=" + createTime +
        "}";
    }
}
