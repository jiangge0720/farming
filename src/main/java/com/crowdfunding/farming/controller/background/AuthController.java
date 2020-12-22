package com.crowdfunding.farming.controller.background;

import com.crowdfunding.farming.config.JwtProperties;
import com.crowdfunding.farming.pojo.UserInfo;
import com.crowdfunding.farming.service.AuthService;
import com.crowdfunding.farming.utils.CookieUtils;
import com.crowdfunding.farming.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jiang-gege
 * 2020/11/120:01
 */
@RestController
@EnableConfigurationProperties(JwtProperties.class)
@Api("权限服务接口")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties prop;

    /**
     * 登录授权
     * @param nickName
     * @param password
     * @param request
     * @param response
     * @return
     */
    @GetMapping("accredit")
    @ApiOperation(value = "登录授权,返回token" , notes = "登录授权")
    public ResponseEntity<String> accredit(
            @RequestParam("nickName") String nickName,
            @RequestParam("password") String password,
            HttpServletRequest request,
            HttpServletResponse response){
        //登录校验
        String token = this.authService.authentication(nickName,password);
        if(StringUtils.isBlank(token)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        // 将token写入cookie,并指定httpOnly为true，防止通过JS获取和修改
        CookieUtils.setCookie(request,response, prop.getCookieName(),token);
//        return ResponseEntity.ok().build();
        return ResponseEntity.ok(token);
    }

    /**
     * 验证用户信息
     * @param token
     * @return
     */
    @GetMapping("verify")
    @ApiOperation(value = "验证用户，返回用户信息",notes = "验证用户信息" )
    public ResponseEntity<UserInfo> verifyUser(@CookieValue("FARMING_TOKEN")String token,
                                               HttpServletRequest request,
                                               HttpServletResponse response){
        try{
            //获取token信息
            UserInfo userInfo = JwtUtils.getInfoFromToken(token,prop.getPublicKey());
            //如果成功，刷新token
            String newToken = JwtUtils.generateToken(userInfo,
                    prop.getPrivateKey(), prop.getExpire());
            // 然后写入cookie中
            // 将token写入cookie,并指定httpOnly为true，防止通过JS获取和修改
            CookieUtils.setCookie(request, response, prop.getCookieName(),
                    newToken);
            // 成功后直接返回
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            // 抛出异常，证明token无效，直接返回401
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}