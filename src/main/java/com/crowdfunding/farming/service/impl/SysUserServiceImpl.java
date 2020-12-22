package com.crowdfunding.farming.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.crowdfunding.farming.mapper.SysUserMapper;
import com.crowdfunding.farming.pojo.SysUser;
import com.crowdfunding.farming.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jiang-gege
 * 2020/10/3114:13
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public Boolean register(SysUser sysUser) {

        String nickName = sysUser.getNickName();
        SysUser record = new SysUser();
        record.setNickName(nickName);
        List<SysUser> users = sysUserMapper.select(record);
        if(CollectionUtil.isNotEmpty(users)){
            return false;
        }
        boolean result = this.sysUserMapper.insertSelective(sysUser) == 1;
        return result;
    }

    @Override
    public SysUser queryUser(String nickName, String password) {
        // 查询
        SysUser record = new SysUser();
        record.setNickName(nickName);
        SysUser user = this.sysUserMapper.selectOne(record);
        // 校验用户名
        if (user == null) {
            return null;
        }
        // 校验密码
        if (!user.getPassword().equals(password)) {
            return null;
        }
        // 用户名密码都正确
        return user;
    }
}