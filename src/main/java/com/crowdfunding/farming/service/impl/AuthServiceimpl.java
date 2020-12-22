package com.crowdfunding.farming.service.impl;

import com.crowdfunding.farming.config.JwtProperties;
import com.crowdfunding.farming.pojo.SysUser;
import com.crowdfunding.farming.pojo.UserInfo;
import com.crowdfunding.farming.service.AuthService;
import com.crowdfunding.farming.service.SysUserService;
import com.crowdfunding.farming.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;


/**
 * @author Jiang-gege
 * 2020/11/120:05
 */
@Service
@EnableConfigurationProperties(JwtProperties.class)
public class AuthServiceimpl implements AuthService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private JwtProperties jwtProp;

    private static Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Override
    public String authentication(String nickName, String password) {
        try{
            //查询用户
            SysUser sysUser = sysUserService.queryUser(nickName,password);
            if(sysUser == null){
                logger.info("用户信息不存在，{}",nickName);
                return null;
            }
            //生成token
            String token = JwtUtils.generateToken(
                    new UserInfo(sysUser.getUserId(),sysUser.getNickName()),
                    jwtProp.getPrivateKey(), jwtProp.getExpire());
            return token;
        }catch (Exception e){
            return null;
        }
    }

}