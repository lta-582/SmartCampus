package com.taian.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taian.entity.User;
import com.taian.mapper.UserMapper;
import com.taian.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Integer openidToUserid(String openId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("open_id", openId);
        queryWrapper.select("user_id");
        List userIdList = baseMapper.selectList(queryWrapper);
        User user = (User) userIdList.get(0);
        Integer userId = user.getUserId();
        return userId;
    }
    @Override
    public IPage<User> getUserByOpr(Page<User> page , QueryWrapper<User> queryWrapper) {

        //对id进行降序处理
        queryWrapper.orderByDesc("user_id");

        //进行分页处理
        Page<User> userPage = baseMapper.selectPage(page,queryWrapper);

        return userPage;
    }

}
