package com.crowdfunding.farming.controller.background;

import com.crowdfunding.farming.pojo.SysUser;
import com.crowdfunding.farming.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Jiang-gege
 * 2020/10/3114:13
 */
@RestController
@Api("后台用户服务接口")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 注册
     * @param sysUser
     * @return
     */
    @PostMapping("register")
    @ApiOperation(value = "注册用户，返回结果", notes = "注册")
    public ResponseEntity<Void> register(@Valid SysUser sysUser){

        Boolean result = this.sysUserService.register(sysUser);
        if(result == null || !result){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 登陆
     * @param nickName
     * @param password
     * @return
     */
    @GetMapping("query")
    @ApiOperation(value = "用户登陆，返回用户信息", notes = "登陆")
    public ResponseEntity<SysUser> queryUser(
            @RequestParam("nickName") String nickName,
            @RequestParam("password") String password
            ){
        SysUser user = sysUserService.queryUser(nickName,password);
        if(user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(user);
    }
}