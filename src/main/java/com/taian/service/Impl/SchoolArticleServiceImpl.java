package com.taian.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taian.entity.SchoolArticle;
import com.taian.mapper.SchoolArticleMapper;
import com.taian.service.ISchoolArticleService;
import org.springframework.stereotype.Service;

@Service
public class SchoolArticleServiceImpl extends ServiceImpl<SchoolArticleMapper, SchoolArticle> implements ISchoolArticleService {
    @Override
    public IPage<SchoolArticle> getArticleByOpr(Page<SchoolArticle> page, QueryWrapper<SchoolArticle> queryWrapper) {

        //对id进行降序处理
        queryWrapper.orderByDesc("article_id");

        //进行分页处理
        Page<SchoolArticle> schoolArticlePage = baseMapper.selectPage(page, queryWrapper);

        return schoolArticlePage;

    }
}
