package com.crowdfunding.farming.controller.client;

import com.crowdfunding.farming.utils.CheckUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author Jiang-gege
 * 2020/9/2412:55
 */
@Slf4j
@RestController
@Api("微信服务接口")
public class WeixinController {


    //对接微信公众号
    @RequestMapping(value = "/",method= RequestMethod.GET)
    public String checkSignature(@RequestParam(name="signature") String signature,
                                 @RequestParam(name="timestamp") String timestamp,
                                 @RequestParam(name="nonce") String nonce,
                                 @RequestParam(name="echostr") String echostr) {
        //验证shal加密后的字符串和signature是否一样
        boolean b = new CheckUtil().validParams(signature,timestamp,nonce);
        if (b){
            return (echostr);
        }
        return null;
    }


    //获取code
    @RequestMapping(value = "/sendRedirect", method = { RequestMethod.POST, RequestMethod.GET })
    public void sendRedirect(@RequestBody Map<String, Object> reqMap, HttpServletResponse response) {
        if (reqMap.get("redirect_uri") == null) {
            log.info("redirect_uri为空!!!");
            return;
        }
        String redirect_uri = reqMap.get("redirect_uri").toString();
        redirect_uri = URLDecoder.decode(redirect_uri);
        System.out.println(redirect_uri);
        //回调地址
         //redirect_uri = URLDecoder.decode(redirect_uri);
        //授权页面地址
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
        url = url.replace("APPID", "wx1acf95e2535a6f1a").replace("REDIRECT_URI", "http://47.98.216.68").replace("SCOPE", "snsapi_userinfo");
        try {
            //重定向到授权页面   跳转回调redirect_uri，应当使用https链接来确保授权code的安全性
            response.sendRedirect(url);
        } catch (Exception e) {
        }
    }

    @RequestMapping(value = "/hello", method = RequestMethod.POST )
    public String aaa (@RequestBody Map<String, Object> reqMap, HttpServletResponse response){
        String redirect_uri = reqMap.get("redirect_uri").toString();
        System.out.println(redirect_uri);
        String url = URLDecoder.decode(redirect_uri);
        System.out.println(redirect_uri);
        if (url.equals(redirect_uri)){
            return "ok";
        }
        return url;
    }
}