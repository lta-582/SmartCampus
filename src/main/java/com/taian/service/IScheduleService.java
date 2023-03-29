package com.taian.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taian.entity.Schedule;

public interface IScheduleService extends IService<Schedule> {
    IPage<Schedule> getScheduleByOpr (Page<Schedule> page, QueryWrapper<Schedule> queryWrapper);
}
