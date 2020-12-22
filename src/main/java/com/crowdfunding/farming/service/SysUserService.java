package com.crowdfunding.farming.service;

import com.crowdfunding.farming.pojo.SysUser;

/**
 * @author Jiang-gege
 * 2020/10/3114:12
 */
public interface SysUserService {
    Boolean register(SysUser sysUser);

    SysUser queryUser(String nickName, String password);
}
