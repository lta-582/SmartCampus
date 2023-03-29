package com.taian.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class CommentVO {

    @TableId(type = IdType.AUTO)
    Integer commentId;

    Integer commentableId;

    String commentableType;

    Integer userId;

    String content;

    Long createTime;

    String avatar;

    String nick;
}
