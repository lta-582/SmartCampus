package com.taian.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taian.entity.schsys.SchsysExamInfo;
import com.taian.mapper.schsys.SchsysExamInfoMapper;
import com.taian.service.ISchsysExamInfoService;
import org.springframework.stereotype.Service;

@Service
public class SchsysExamInfoServiceImpl extends ServiceImpl<SchsysExamInfoMapper, SchsysExamInfo> implements ISchsysExamInfoService {
}
