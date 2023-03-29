package com.taian.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.taian.entity.User;
import com.taian.service.UserService;
import com.taian.util.Result;
import com.taian.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getUserCard")
    public Result getUserCard(@RequestHeader("token") String token){
        //获取用户OpenId
        String openId = TokenUtil.tokenUtil(token);

        if (openId == ""){
            return Result.fail("token失效请重新登录 ");
        }else {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("open_id",openId);
            User user = userService.getOne(userQueryWrapper);

            //包装返回数据
            Map<String, String> map = new LinkedHashMap<>();
            map.put("avatar",user.getAvatar());
            map.put("nick",user.getNick());
            map.put("studentId",user.getStudentId());
            map.put("signature",user.getSignature());


            return Result.ok(map);
        }
    }
}
