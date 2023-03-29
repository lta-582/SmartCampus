package com.taian.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taian.entity.Image;
import com.taian.mapper.ImageMapper;
import com.taian.service.IImageService;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements IImageService {

}
