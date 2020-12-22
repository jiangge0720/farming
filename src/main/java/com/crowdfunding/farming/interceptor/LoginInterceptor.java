package com.crowdfunding.farming.interceptor;

import com.crowdfunding.farming.config.JwtProperties;
import com.crowdfunding.farming.pojo.UserInfo;
import com.crowdfunding.farming.utils.CookieUtils;
import com.crowdfunding.farming.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jiang-gege
 * 2020/11/38:43
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private JwtProperties jwtProperties;

    private static final ThreadLocal<UserInfo> t1 =new ThreadLocal<>();

    public LoginInterceptor(JwtProperties jwtProperties){
        this.jwtProperties = jwtProperties;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //查询token
        String token = CookieUtils.getCookieValue(request,"FARMING_TOKEN");
        if(StringUtils.isBlank(token)){
            //未登录，返回401
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        //有token,查询用户
        try{
            UserInfo user = JwtUtils.getInfoFromToken(token,jwtProperties.getPublicKey());
            //放入线程域
            t1.set(user);
            return true;
        }catch (Exception e){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        t1.remove();
    }

    public static UserInfo getUserInfo(){
        return t1.get();
    }
}