package com.taian.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.taian.entity.User;
import com.taian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserLoginInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    static UserLoginInterceptor userLoginInterceptor;

    @PostConstruct
    public void init() {
        userLoginInterceptor = this;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取 http的header中获得 token信息
        String token = request.getHeader("token");

        //token不存在
        if (token == null || token.equals("")) {
            return false;
        }

        //判断是否过期，过期的话，通过数据库校验，发送token的更新值，更新数据库token
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("token", token);
            User user = userLoginInterceptor.userService.getOne(queryWrapper);
            System.out.println(user);
            String openId = user.getOpenId();
            if (openId == "") {
                return false;
            } else {
                String newToken = JwtHelper.createToken(openId);
                System.out.println("token过期,返回响应");

                //更新数据库的token
                user.setToken(newToken);
                if (userLoginInterceptor.userService.updateById(user)) {
                    response.setStatus(224);
                    response.setHeader("token", newToken);
                }
                return false;
            }
        } else
            return true;
    }
}
