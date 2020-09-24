package com.crowdfunding.farming.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Jiang-gege
 * 2020/9/2412:55
 */

@RestController
public class WeixinController {


    @GetMapping("/wx")
    public String test(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");
        PrintWriter out = resp.getWriter();
        if (com.crowdfunding.farming.utils.CheckUtil.checkSignature(signature, timestamp, nonce)) {
            return echostr;
        } else {
            System.out.println("不匹配");
            return null;
        }
    }

}