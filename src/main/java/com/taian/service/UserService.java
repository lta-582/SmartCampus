package com.taian.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taian.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public interface UserService extends IService<User> {
    Integer openidToUserid(String openId);
    IPage<User> getUserByOpr (Page<User> page, QueryWrapper<User> queryWrapper);
}
