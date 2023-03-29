package com.taian.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taian.entity.SchoolArticle;

public interface ISchoolArticleService extends IService<SchoolArticle> {
    IPage<SchoolArticle> getArticleByOpr (Page<SchoolArticle> page, QueryWrapper<SchoolArticle> queryWrapper);

}