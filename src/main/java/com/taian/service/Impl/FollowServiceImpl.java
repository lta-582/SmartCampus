package com.taian.service.Impl;

import com.taian.entity.Follow;
import com.taian.mapper.FollowMapper;
import com.taian.service.IFollowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements IFollowService {

}
