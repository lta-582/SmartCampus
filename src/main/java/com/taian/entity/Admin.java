package com.taian.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Admin {

    @TableId(type = IdType.AUTO)
    Integer admin_id;

    String adminName;

    String adminPassword;
}
