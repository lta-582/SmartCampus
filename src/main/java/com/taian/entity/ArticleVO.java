package com.taian.entity;

import lombok.Data;

@Data
public class ArticleVO {

    private Integer articleId;

    private Integer imageCount;

    private Integer userId;

    private String content;

    private String locationName;

    private Integer commentCount;

    private Integer voteCount;

    private Long createTime;

    private String imagePath;

    private String nick;

    private String avatar;

    private Integer isVote;
}
