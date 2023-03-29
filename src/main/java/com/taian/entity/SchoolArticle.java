package com.taian.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SchoolArticle {

    @TableId(type = IdType.AUTO)
    Integer articleId;

    String title;

    String contentText;

    Long createTime;

    String imagePath;
}
