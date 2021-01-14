//package com.crowdfunding.farming.config;
//
//import com.crowdfunding.farming.interceptor.LoginInterceptor;
//import com.crowdfunding.farming.config.JwtProperties;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * @author Jiang-gege
// * 2020/11/39:33
// */
//@Configuration
//@EnableConfigurationProperties(JwtProperties.class)
//public class MvcConfig implements WebMvcConfigurer {
//
//    @Autowired
//    private JwtProperties jwtProperties;
//
//    @Bean
//    public LoginInterceptor loginInterceptor(){
//        return new LoginInterceptor(jwtProperties);
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor());
//
//    }
//}