package com.crowdfunding.farming.service.impl;

import com.crowdfunding.farming.mapper.UserMapper;
import com.crowdfunding.farming.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jiang-gege
 * 2020/10/2114:27
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
}