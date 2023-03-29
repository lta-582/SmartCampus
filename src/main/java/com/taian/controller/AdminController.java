package com.taian.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taian.entity.*;
import com.taian.mapper.ArticleMapper;
import com.taian.mapper.CommentMapper;
import com.taian.service.*;
import com.taian.util.DateUtils;
import com.taian.util.JwtHelper;
import com.taian.util.PythonUtils;
import com.taian.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

    @Autowired
    IArticleService iArticleService;

    @Autowired
    ISchoolArticleService iSchoolArticleService;

    @Autowired(required = false)
    ArticleMapper articleMapper;

    @Autowired
    IRotationImageService iRotationImageService;

    @Autowired(required = false)
    CommentMapper commentMapper;

    @Autowired
    ICommentService iCommentService;

    @PostMapping("/login")
    public Result adminLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ){
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.eq("admin_name",username);
        Admin byId = adminService.getOne(adminQueryWrapper);
        String token = JwtHelper.createToken(username);
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("token",token);
        if (byId==null)
        {
            return Result.fail("账号不存在");
        }
        if (byId.getAdminPassword().equals(password)){
            return Result.ok(stringStringMap);
        }else {
            return Result.fail("密码错误");
        }
    }

    @PostMapping("/user/list")
    public Result getAllUser(
            @RequestHeader("token") String token,
            @RequestParam("page") Integer pageNo,
            @RequestParam("limit") Integer pageSize,
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value ="studentId",required = false) String studentId,
            @RequestParam(value ="userId",required = false) String userId,
            @RequestParam(value ="userType",required = false) String userType
    ){
        if (JwtHelper.isExpiration(token)) {
            return Result.fail("请重新登录");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String, Object> queryParamsMap = new HashMap<>();
        queryParamsMap.put("name",name);
        queryParamsMap.put("student_id",studentId);
        queryParamsMap.put("user_id",userId);
        queryParamsMap.put("user_type",userType);

        queryWrapper.allEq(queryParamsMap,false);

        Page<User> page = new Page<>(pageNo,pageSize);
        IPage<User> userByOpr = userService.getUserByOpr(page,queryWrapper);
        return Result.ok(userByOpr);
    }

    @GetMapping("user/publish")
    public Result changeIsPublish(
            Integer userId,Integer isPublish,String token
    ){
        if (!JwtHelper.isExpiration(token)){
            UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
            userUpdateWrapper.eq("user_id",userId);
            userUpdateWrapper.set("is_publish",isPublish);
            userService.update(userUpdateWrapper);
            return Result.ok();
        }else return Result.fail("token失效");
    }
    @GetMapping("user/active")
    public Result changeIsActive(
            Integer userId,Integer isActive,String token
    ){
        if (!JwtHelper.isExpiration(token)){
            UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
            userUpdateWrapper.eq("user_id",userId);
            userUpdateWrapper.set("is_active",isActive);
            userService.update(userUpdateWrapper);
            return Result.ok();
        }else return Result.fail("token失效");
    }

    @PostMapping("/user/save")
    public Result userSave(
            User user
    ){
        userService.saveOrUpdate(user);
        return Result.ok();
    }

    @Transactional
    @DeleteMapping("/user/delete")
    public Result deleteUser(
            Integer ids
    ){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",ids);
        userService.remove(queryWrapper);
        return Result.ok();
    }

    @GetMapping("/user/flashCache")
    public Result flashCache(
            String userId
    ){
        User byId = userService.getById(userId);
        PythonUtils pythonUtils = new PythonUtils();
        pythonUtils.dataStorage(byId.getStudentId(),byId.getPassWord());
        return Result.ok();
    }

    @PostMapping("/article/list")
    public Result getArticle(
            @RequestHeader("token") String token,
            @RequestParam("page") Integer pageNo,
            @RequestParam("limit") Integer pageSize,
            @RequestParam(value = "userId",required = false) String userId,
            @RequestParam(value = "articleId",required = false) String articleId,
            @RequestParam(value = "nick",required = false) String nick
    ){
//        String openId = TokenUtil.tokenUtil(token);
        System.out.println(userId+articleId+nick);

        QueryWrapper<ArticleVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");

        Map<String, Object> queryParamsMap = new HashMap<>();
        queryParamsMap.put("userId",userId);
        queryParamsMap.put("articleId",articleId);
        queryParamsMap.put("nick",nick);

        queryWrapper.allEq(queryParamsMap,false);

        //设置分页信息
        Page<ArticleVO> articlePage = new Page<>(pageNo, pageSize);
        //调用Service进行处理
        IPage<ArticleVO> articleIPage = articleMapper.getFullArticle(articlePage, queryWrapper);

        List<ArticleVO> records = articleIPage.getRecords();
        return Result.ok(records);
    }

    @Transactional
    @DeleteMapping("/article/delete")
    public Result deleteArticle(
            Integer ids
    ){
        System.out.println(ids);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id",ids);
        iArticleService.remove(queryWrapper);
        return Result.ok();
    }

    @PostMapping("schoolArticle/list")
    public Result schoolArticleList(
            @RequestParam("page") Integer pageNo,
            @RequestParam("limit") Integer pageSize
    ){
        QueryWrapper<SchoolArticle> schoolArticleQueryWrapper = new QueryWrapper<>();
        Page<SchoolArticle> schoolArticlePage = new Page<>(pageNo,pageSize);
        IPage<SchoolArticle> articleByOpr = iSchoolArticleService.getArticleByOpr(schoolArticlePage, schoolArticleQueryWrapper);
        return Result.ok(articleByOpr);
    }

    @PostMapping("schoolArticle/save")
    public Result schoolArticleSave(SchoolArticle schoolArticle){
        schoolArticle.setCreateTime(DateUtils.getDateNowTimeNo());
        return Result.ok(iSchoolArticleService.saveOrUpdate(schoolArticle));
    }

    @DeleteMapping("/schoolArticle/delete")
    public Result schoolArticleDelete(
            Integer ids
    ){
        QueryWrapper<SchoolArticle> schoolArticleQueryWrapper = new QueryWrapper<>();
        schoolArticleQueryWrapper.eq("article_id",ids);
        iSchoolArticleService.remove(schoolArticleQueryWrapper);
        return Result.ok();
    }
    @PostMapping("/upload/image")
    public String articleImgUpload(
            //spring中提供的接收文件的对象
            @RequestPart("file") MultipartFile multipartFile
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
    @PostMapping("image/list")
    public Result imageList(
            @RequestParam("page") Integer pageNo,
            @RequestParam("limit") Integer pageSize
    ){
        QueryWrapper<RotationImage> rotationImageQueryWrapper = new QueryWrapper<>();
        Page<RotationImage> rotationImagePage = new Page<>(pageNo,pageSize);
        IPage<RotationImage> ipage = iRotationImageService.getImageByOpr(rotationImagePage,rotationImageQueryWrapper);
        return Result.ok(ipage);
    }

    @PostMapping("image/save")
    public Result imageSave(RotationImage rotationImage){
        rotationImage.setUploadTime(DateUtils.getDateNowTimeNo());
        return Result.ok(iRotationImageService.saveOrUpdate(rotationImage));
    }

    @DeleteMapping("/image/delete")
    public Result imageDelete(
            Integer ids
    ){
        QueryWrapper<RotationImage> schoolArticleQueryWrapper = new QueryWrapper<>();
        schoolArticleQueryWrapper.eq("image_id",ids);
        iRotationImageService.remove(schoolArticleQueryWrapper);
        return Result.ok();
    }

    @PostMapping("/comment/list")
    public Result getByOpr(
            @RequestParam("page") Integer pageNo,
            @RequestParam("limit") Integer pageSize,
            @RequestParam("articleId") Integer articleId
    ){
        System.out.println("-----------------"+pageNo+pageSize+articleId);

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

    @DeleteMapping("/comment/delete")
    public Result commentDelete(
            Integer ids
    ){
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id",ids);
        iCommentService.remove(queryWrapper);
        return Result.ok("删除成功");
    }

}
