package com.taian.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class User {

    @TableId(type = IdType.AUTO)
    Integer userId;

    String openId;

    String studentId;

    String passWord;

    String sessionKey;

    String token;

    String name;

    String telephone;

    String signature;

    String nick;

    String avatar;

    String userType;

    Integer isActive;

    Integer isPublish;

    String sex;
}
