package com.taian.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taian.entity.Article;
import com.taian.entity.ArticleVO;
import com.taian.entity.User;
import com.taian.entity.Vote;
import com.taian.mapper.ArticleMapper;
import com.taian.service.IArticleService;
import com.taian.service.IVoteService;
import com.taian.service.UserService;
import com.taian.util.DateUtils;
import com.taian.util.Result;
import com.taian.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    IArticleService articleService;

    @Autowired
    UserService userService;

    @Autowired(required = false)
    private ArticleMapper articleMapper;

    @Autowired
    private IVoteService iVoteService;

    @PostMapping("/add")
    public Result add(
            @RequestBody Map<String, String> map,
            @RequestHeader("token") String token
            ){
        Article article = new Article();
        String openId  =  TokenUtil.tokenUtil(token);
        Long dateNo = DateUtils.getDateNowTimeNo();

        System.out.println("map的数据是：" + map.toString());

        User user = userService.getOne(new QueryWrapper<User>().eq("open_id", openId));
        if (openId == ""){
            return Result.fail("token失效请重新登录 ");
        }else {
            if (user.getIsPublish()==0) {
                return Result.fail("没有发布权限");
            }
            Integer imageCount = Integer.valueOf(map.get("imageCount"));

            article.setUserId(user.getUserId());
            article.setContent(map.get("context"));
            article.setLocationName(map.get("locationName"));
            article.setImageCount(imageCount);
            article.setVoteCount(0);
            article.setCommentCount(0);
            article.setCreateTime(dateNo);
            article.setImagePath(map.get("imagePath"));
            articleService.save(article);
            System.out.println(article);
        }
        return Result.ok(article);
    }

    @GetMapping("/getMy/{pageNo}/{pageSize}")
    public Result getMy(
            @RequestHeader("token") String token,
            @PathVariable("pageNo") Integer pageNo,
            @PathVariable("pageSize") Integer pageSize
    ){
        String openId = TokenUtil.tokenUtil(token);
        Integer userId = userService.openidToUserid(openId);

        Page<Article> articlePage = new Page<>(pageNo,pageSize);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        IPage<Article> articleByOpr = articleService.getArticleByOpr(articlePage, queryWrapper);
        return Result.ok(articleByOpr);
    }

    @PostMapping("/uploadImage")
    public String articleImgUpload(
            //spring中提供的接收文件的对象
            @RequestPart("multipartFile") MultipartFile multipartFile
    ){
        //改文件名字
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String originalFilename = multipartFile.getOriginalFilename();
        int i = originalFilename.lastIndexOf(".");
        String newFileName = uuid.concat(originalFilename.substring(i));
        System.out.println(newFileName);

        //保存文件，将文件发送到第三方服务
        String portraitPath = "E:/upload/datasrc/article/".concat(newFileName);

        try {
            multipartFile.transferTo(new File(portraitPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String outPath = "http://localhost:8080/taian/upload/article/" + newFileName;

        return outPath;
    }

    @PostMapping("/delImage")
    public Result delImage(
            @RequestBody Map<String, String> map
    ){
        String imagePath = map.get("imagePath");
        Integer index = imagePath.lastIndexOf('/');
        String fileName = imagePath.substring(index+1);
        String localPath = "E:/upload/datasrc/article/" + fileName;
        File file = new File(localPath);
        file.delete();
        return Result.ok();
    }

    @GetMapping("/getAll/{pageNo}/{pageSize}")
    public Result getAll(
            @RequestHeader("token") String token,
            @PathVariable("pageNo") Integer pageNo,
            @PathVariable("pageSize") Integer pageSize
    ){
        String openId = TokenUtil.tokenUtil(token);

        QueryWrapper<ArticleVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");

        //设置分页信息
        Page<ArticleVO> articlePage = new Page<>(pageNo, pageSize);
        //调用clazzService进行处理
        IPage<ArticleVO> articleIPage = articleMapper.getFullArticle(articlePage, queryWrapper);

        List<ArticleVO> records = articleIPage.getRecords();
        for (ArticleVO item : records){
            QueryWrapper<Vote> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("user_id",userService.openidToUserid(openId));
            queryWrapper1.eq("votable_id",item.getArticleId());
            queryWrapper1.eq("type","article");
            if (iVoteService.getOne(queryWrapper1) == null){
                item.setIsVote(0);
            }else {
                item.setIsVote(1);
            }
        }

        return Result.ok(records);
    }

    @GetMapping("/get/{articleId}")
    public Result get(
            @PathVariable("articleId") Integer articleId
    ){
        QueryWrapper<ArticleVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id",articleId);


        ArticleVO fulArticle = articleMapper.getFulArticle(queryWrapper);

        System.out.println(fulArticle);
        return Result.ok(fulArticle);


    }

    @PostMapping("/del")
    public Result del(
            @RequestHeader("token") String token,
            @RequestBody Map<String, Integer> map
    ) {
        String openId = TokenUtil.tokenUtil(token);
        Integer userId = userService.openidToUserid(openId);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("article_id",map.get("articleId"));

        String imagePath = articleService.getOne(queryWrapper).getImagePath();
        Integer index = imagePath.lastIndexOf('/');//http://localhost:8080/taian/upload/article/db59f3ddbe8e48c79cdcf12869679abe.png
        String fileName = imagePath.substring(index+1);
        String localPath = "E:/upload/datasrc/article/" + fileName;
        File file = new File(localPath);
        file.delete();
        articleService.remove(queryWrapper);
        return Result.ok();
    }

    @PostMapping("/vote")
    public Result voteAdd(
            @RequestHeader("token") String token,
            @RequestBody Map<String, String> map
    ){
        String openId = TokenUtil.tokenUtil(token);
        Integer userId = userService.openidToUserid(openId);
        Integer votableId = Integer.valueOf(map.get("votableId"));
        String type = map.get("type");
        System.out.println(type+type.getClass());
        QueryWrapper<Vote> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("votable_id",votableId);
        queryWrapper.eq("type",type);
        System.out.println((type.equals("article")));
        if (type.equals("article")){
            Article article = articleService.getOne(new QueryWrapper<Article>().eq("article_id", votableId));
            Integer voteCount = article.getVoteCount();
            //如果在表中没有找到数据就点赞成功，就+1
            if (iVoteService.getOne(queryWrapper) == null){
                voteCount++;
                Vote vote = new Vote();
                vote.setUserId(userId);
                vote.setVotableId(votableId);
                vote.setType(type);
                iVoteService.save(vote);
            }else {
                voteCount--;
                iVoteService.remove(queryWrapper);
            }
            article.setVoteCount(voteCount);
            articleService.update(new UpdateWrapper<Article>().eq("article_id",votableId).set("vote_count",voteCount));

            System.out.println("完成添加");
        }
        return Result.ok();
    }
}
