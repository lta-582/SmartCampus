package com.taian.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Schedule {

    @TableId(type = IdType.AUTO)
    private Integer scheduleId;

    private Integer userId;

    private String content;

    private Long eventDate;

    private Integer eventStart;

    private Integer eventEnd;

    private Long createTime;
}
