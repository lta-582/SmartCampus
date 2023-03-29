package com.taian.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taian.entity.RotationImage;
import com.taian.entity.SchoolArticle;
import com.taian.entity.User;
import com.taian.service.AdminService;
import com.taian.service.IRotationImageService;
import com.taian.service.ISchoolArticleService;
import com.taian.service.UserService;
import com.taian.util.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/sys")
public class SystemController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private IRotationImageService iRotationImageService;

    @Autowired
    private ISchoolArticleService iSchoolArticleService;

    @PostMapping("/register")
    public Result user_register(@RequestBody Map<String , String> map) {

        String code = map.get("code");
        String studentId = map.get("studentId");
        String passWord = map.get("passWord");
        System.out.println(map);

        if(map.get("userType").equals("学生")){
            //通过调用python判断账号密码是否正确，用这个账号密码是否可以登录学校的网址
            PythonUtils pythonUtils = new PythonUtils();
            String status = pythonUtils.getValue(studentId, passWord);
            if (status.equals("error")) {
                return Result.fail("密码错误");
            }else if (status.equals("none")){
                return Result.fail("账号不存在");
            }else if(status.equals("pass")){
                return registSuccess(code, studentId, passWord,"学生");
            }
            else {
                return Result.fail("未知错误");
            }
        }else if (map.get("userType").equals("教师")){
            return registSuccess(code, studentId, passWord,"教师");
        }else {
            return Result.fail();
        }
    }

    //taian/sys/headerImgUpload
    @ApiOperation("头像上传统一入口")
    @PostMapping("/headerImgUpload")
    public String headerImgUpload(
            //spring中提供的接收文件的对象
            @RequestPart("multipartFile") MultipartFile multipartFile
    ){

        //获取openid
//        String openId = TokenUtil.tokenUtil(token);
//
        //改文件名字
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String originalFilename = multipartFile.getOriginalFilename();
        int i = originalFilename.lastIndexOf(".");
        String newFileName = uuid.concat(originalFilename.substring(i));
        System.out.println(newFileName);

        //保存文件，将文件发送到第三方服务
        String portraitPath = "E:/upload/datasrc/heard/".concat(newFileName);

        try {
            multipartFile.transferTo(new File(portraitPath));
            System.out.println("图片上传成功");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String outPath = "http://localhost:8080/taian/upload/heard/" + newFileName;


        return outPath;
    }

    @PostMapping("/updateInfo")
    public Result updateInfo(
            @RequestHeader("token") String token,
            @RequestBody Map<String ,String> map
    ){
        System.out.println(map);
        String avatar = map.get("avatar");
        String nick = map.get("nick");
        String signature = map.get("signature");

        String openId  =  TokenUtil.tokenUtil(token);
        if (openId == ""){
            return Result.fail("token失效请重新登录 ");
        }else {
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.eq("open_id", openId);
            updateWrapper.set("avatar",avatar);
            updateWrapper.set("nick",nick);
            updateWrapper.set("signature",signature);

            userService.update(updateWrapper);
            System.out.println("修改了数据");
        }
        return Result.ok();
    }

    @PostMapping("/unbind")
    public Result unbind(
            @RequestHeader("token") String token
    ){
        String openId = TokenUtil.tokenUtil(token);
        Integer userId = userService.openidToUserid(openId);
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id",userId);
        updateWrapper.set("open_id","null");
        updateWrapper.set("session_key","null");
        updateWrapper.set("token","null");
        userService.update(updateWrapper);
        return Result.ok();
    }

    public Result registSuccess(String code, String studentId, String passWord ,String type){
        // 1.接收小程序发送的code
        // 2.开发者服务器 登录凭证校验接口 appi + appsecret + code
        JSONObject SessionKeyOpenId = WeChatUtil.getSessionKeyOrOpenId(code);
        // 3.接收微信接口服务 获取返回的参数
        String openid = SessionKeyOpenId.getString("openid");
        String sessionKey = SessionKeyOpenId.getString("session_key");
        String token = JwtHelper.createToken(openid);
        System.out.println(SessionKeyOpenId);

        // 5.根据返回的User实体类，判断用户是否是新用户，是的话，将用户信息存到数据库；
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",studentId);
        queryWrapper.eq("user_type",type);
        User user = userService.getOne(queryWrapper);
        System.out.println("一个user对象"+user.toString());
        if (user!=null&&user.getIsActive()==0)
            return Result.fail("账号锁定");

        //学生是通过验证的，所以可以验证完信息
        if (type.equals("学生"))
        {
            if (user == null) {
                // 用户信息入库
                user = new User();
                user.setOpenId(openid);
                user.setStudentId(studentId);
                user.setPassWord(passWord);
                user.setSessionKey(sessionKey);
                user.setToken(token);
                user.setAvatar("http://localhost:8080/taian/upload/heard/avatar.jpg");
                user.setNick("微信用户");
                user.setSignature("这个人很懒，什么都没有留下。");
                user.setIsActive(1);
                user.setIsPublish(1);
                userService.save(user);
                System.out.println("创建新用户成功！");
            } else {
                //不是新用户的话重新绑定用户就行了，但是密码要重置
                user.setPassWord(passWord);
                user.setSessionKey(sessionKey);
                user.setToken(token);
                user.setOpenId(openid);
                userService.updateById(user);
                System.out.println("重新绑定用户成功！");
            }

            //用户入库之后，对信息系统得到的数据进行保存
            PythonUtils pythonUtils = new PythonUtils();
            pythonUtils.dataStorage(studentId,passWord);

            return Result.ok(user);
        }else if (type.equals("教师")){//教师系统只能后台添加，所以不能注册
            System.out.println(user.getPassWord()+":"+passWord);
            if (user.getPassWord().equals(passWord))
            {
                user.setOpenId(openid);
                user.setSessionKey(sessionKey);
                user.setToken(token);
                userService.saveOrUpdate(user);
                return Result.ok(user);
            }
            else
                return Result.fail("密码错误");
        }
        else return Result.fail("用户类型有误");
    }

    @GetMapping("/getRotationImage")
    public Result getRotationImage(){
        QueryWrapper<RotationImage> rotationImageQueryWrapper = new QueryWrapper<>();
        return Result.ok(iRotationImageService.list(rotationImageQueryWrapper));
    }

    @GetMapping("schoolArticle/list/{page}/{limit}")
    public Result schoolArticleList(
            @PathVariable("page") Integer pageNo,
            @PathVariable("limit") Integer pageSize
    ){
        QueryWrapper<SchoolArticle> schoolArticleQueryWrapper = new QueryWrapper<>();
        Page<SchoolArticle> schoolArticlePage = new Page<>(pageNo,pageSize);
        IPage<SchoolArticle> articleByOpr = iSchoolArticleService.getArticleByOpr(schoolArticlePage, schoolArticleQueryWrapper);
        return Result.ok(articleByOpr);
    }
}
