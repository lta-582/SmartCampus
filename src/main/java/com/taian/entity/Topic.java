package com.taian.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Topic对象", description = "话题表")
public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("话题ID")
    @TableId(value = "topic_id", type = IdType.AUTO)
    private Integer topicId;

    @ApiModelProperty("话题名称")
    private String name;

    @ApiModelProperty("话题描述")
    private String description;

    @ApiModelProperty("文章数量")
    private Integer articleCount;

    @ApiModelProperty("关注者数量")
    private Integer followerCount;

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }
    public Integer getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }

    @Override
    public String toString() {
        return "Topic{" +
            "topicId=" + topicId +
            ", name=" + name +
            ", description=" + description +
            ", articleCount=" + articleCount +
            ", followerCount=" + followerCount +
        "}";
    }
}
