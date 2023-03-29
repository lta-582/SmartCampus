package com.taian.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taian.entity.Schedule;
import com.taian.mapper.ScheduleMapper;
import com.taian.service.IScheduleService;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule> implements IScheduleService  {

    @Override
    public IPage<Schedule> getScheduleByOpr(Page<Schedule> page ,QueryWrapper<Schedule> queryWrapper) {

        //对id进行降序处理
        queryWrapper.orderByDesc("create_time");

        //进行分页处理
        Page<Schedule> schedulePage = baseMapper.selectPage(page,queryWrapper);

        return schedulePage;
    }
}
