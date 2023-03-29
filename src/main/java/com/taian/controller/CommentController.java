package com.taian.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taian.entity.Article;
import com.taian.entity.Comment;
import com.taian.entity.CommentVO;
import com.taian.mapper.CommentMapper;
import com.taian.service.IArticleService;
import com.taian.service.ICommentService;
import com.taian.service.UserService;
import com.taian.util.DateUtils;
import com.taian.util.Result;
import com.taian.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired(required = false)
    CommentMapper commentMapper;

    @Autowired
    IArticleService iArticleService;

    @Autowired
    UserService userService;

    @Autowired
    ICommentService iCommentService;


    @GetMapping("/get/{articleId}/{pageNo}/{pageSize}")
    public Result getByOpr(
            @PathVariable("pageNo") Integer pageNo,
            @PathVariable("pageSize") Integer pageSize,
            @PathVariable("articleId")Integer articleId
    ){

        QueryWrapper<CommentVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("create_time");
        queryWrapper.eq("commentable_id",articleId);
        queryWrapper.eq("commentable_type","article");

        //设置分页信息
        Page<CommentVO> articlePage = new Page<>(pageNo, pageSize);
        //调用clazzService进行处理
        IPage<CommentVO> commentByOpr = commentMapper.getCommentByOpr(articlePage, queryWrapper);

        System.out.println(commentByOpr);

        return Result.ok(commentByOpr);
    }

    @PostMapping("/add")
    public Result add(
            @RequestHeader("token") String token,
            @RequestBody Map<String,Object> map
            ){
        //保存评论
        String openId = TokenUtil.tokenUtil(token);
        Integer userId = userService.openidToUserid(openId);
        Integer articleId = Integer.valueOf(map.get("articleId").toString());
        String commentContext = map.get("commentContext").toString();
        String commentType = map.get("commentType").toString();
        Comment comment = new Comment();
        comment.setCommentableId(articleId);
        comment.setCommentableType(commentType);
        comment.setContent(commentContext);
        comment.setUserId(userId);
        comment.setCreateTime(DateUtils.getDateNowTimeNo());
        iCommentService.save(comment);

        //文章的评论加一
        Article article = iArticleService.getOne(new QueryWrapper<Article>().eq("article_id", articleId));
        article.setCommentCount(article.getCommentCount()+1);
        iArticleService.updateById(article);

        return Result.ok();

    }

    @PostMapping("/del")
    public Result delComment(
            @RequestHeader("token") String token,
            @RequestBody Map<String,Object> map
    ){
        Integer commentUserid = userService.openidToUserid(TokenUtil.tokenUtil(token));
        System.out.println(map.toString());

        Comment comment = iCommentService.getById(map.get("commentId").toString());
        System.out.println("获取的comment"+comment.toString());
        Integer articleUserId = iArticleService.getById(comment.getCommentableId()).getUserId();
        if (comment.getUserId() != commentUserid && comment.getUserId() != articleUserId) {
            return Result.fail("没有删除权限");
        }else {
            iCommentService.removeById(comment.getCommentId());
            return Result.ok();
        }
    }

}
