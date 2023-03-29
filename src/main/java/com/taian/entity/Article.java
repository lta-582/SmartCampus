package com.taian.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "Article对象", description = "文章表")
@Data
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文章ID")
    @TableId(value = "article_id", type = IdType.AUTO)
    private Integer articleId;

    @ApiModelProperty("图片数量")
    private Integer imageCount;

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("原始的正文内容")
    private String content;

    @ApiModelProperty("定位的名字")
    private String locationName;

    @ApiModelProperty("评论数量")
    private Integer commentCount;

    @ApiModelProperty("点赞数量")
    private Integer voteCount;

    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty("图片路径")
    private String imagePath;

    @ApiModelProperty("是否被点赞")
    private Integer isVote;
}
