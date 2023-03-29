package com.taian.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taian.entity.RotationImage;
import com.taian.mapper.RotationImageMapper;
import com.taian.service.IRotationImageService;
import org.springframework.stereotype.Service;

@Service
public class RotationImageServiceImpl extends ServiceImpl<RotationImageMapper, RotationImage> implements IRotationImageService {
    @Override
    public IPage<RotationImage> getImageByOpr(Page<RotationImage> page, QueryWrapper<RotationImage> queryWrapper) {
            //对id进行降序处理
            queryWrapper.orderByDesc("upload_time");

            //进行分页处理
            Page<RotationImage> rotationImagePage = baseMapper.selectPage(page,queryWrapper);

            return rotationImagePage;
        }

}
