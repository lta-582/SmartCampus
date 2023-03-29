package com.taian.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taian.entity.Admin;
import com.taian.mapper.AdminMapper;
import com.taian.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {


}
