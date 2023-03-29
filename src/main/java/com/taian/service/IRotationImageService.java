package com.taian.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taian.entity.RotationImage;

public interface IRotationImageService extends IService<RotationImage> {
    IPage<RotationImage> getImageByOpr(Page<RotationImage> page, QueryWrapper<RotationImage> queryWrapper);
}
