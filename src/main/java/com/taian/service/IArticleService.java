package com.taian.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taian.entity.Article;

public interface IArticleService extends IService<Article> {

    IPage<Article> getAllArticleByOpr(Page<Article> page);

    IPage<Article> getArticleByOpr(Page<Article> page, QueryWrapper<Article> queryWrapper);

//    IPage<ArticleVO> getFillArticle(Page<ArticleVO> page);

}
