package com.taian.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class RotationImage {

    @TableId(type = IdType.AUTO)
    Integer imageId;
    String imagePath;
    Long uploadTime;
}
