package com.taian.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Comment {

    @TableId(type = IdType.AUTO)
    Integer commentId;

    Integer commentableId;

    String commentableType;

    Integer userId;

    String content;

    Long createTime;
}
