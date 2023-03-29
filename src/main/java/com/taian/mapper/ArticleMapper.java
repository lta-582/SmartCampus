package com.taian.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.taian.entity.Article;
import com.taian.entity.ArticleVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ArticleMapper extends BaseMapper<Article> {

    @Select("select article.article_id, article.user_id, article.content, article.location_name, article.vote_count, article.comment_count, article.image_count, article.create_time,user.nick as nick, user.avatar as avatar, article.image_path, article.is_vote \n" +
    "from article, user \n"+
    "where article.user_id=user.user_id\n" +
    "${ew.customSqlSegment}")
    IPage<ArticleVO>  getFullArticle(IPage<ArticleVO> page, @Param("ew") Wrapper wrapper);

    @Select("select article.article_id, article.user_id, article.content, article.location_name, article.vote_count, article.comment_count, article.image_count, article.create_time,user.nick as nick, user.avatar as avatar, article.image_path, article.is_vote \n" +
            "from article INNER JOIN user ON article.user_id=user.user_id\n"+
            "${ew.customSqlSegment}")
    ArticleVO  getFulArticle( @Param("ew") Wrapper wrapper);
}
