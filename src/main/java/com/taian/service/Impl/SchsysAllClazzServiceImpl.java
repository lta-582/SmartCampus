package com.taian.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taian.entity.schsys.SchsysAllClazz;
import com.taian.mapper.schsys.SchsysAllClazzMapper;
import com.taian.service.ISchsysAllClazzService;
import org.springframework.stereotype.Service;

@Service
public class SchsysAllClazzServiceImpl  extends ServiceImpl<SchsysAllClazzMapper, SchsysAllClazz> implements ISchsysAllClazzService {
}
