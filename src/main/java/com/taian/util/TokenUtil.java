package com.taian.util;

public class TokenUtil {
    public static String tokenUtil(String token) {
        //获取token并判断token是否过期
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration) {
            //token过期
            System.out.println("toekn过期");
            return "";
        } else {
            //获取用户OpenId
            String openId = JwtHelper.getOpenId(token);
            return openId;
        }
    }
}
