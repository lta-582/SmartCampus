package com.taian.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taian.entity.Article;
import com.taian.mapper.ArticleMapper;
import com.taian.service.IArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Override
    public IPage<Article> getAllArticleByOpr(Page<Article> page) {
        //来源于mybatis-plus的方法，在接口中继承了IService的方法
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();

        //对id进行降序处理
        queryWrapper.orderByDesc("create_time");

        //进行分页处理
        Page<Article> articlePage = baseMapper.selectPage(page,queryWrapper);

        return articlePage;
    }

    @Override
    public IPage<Article> getArticleByOpr(Page<Article> page, QueryWrapper<Article> queryWrapper) {
        //对id进行降序处理
        queryWrapper.orderByDesc("create_time");

        //进行分页处理
        Page<Article> articlePage = baseMapper.selectPage(page,queryWrapper);

        return articlePage;
    }

}
