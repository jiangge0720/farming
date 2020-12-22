package com.crowdfunding.farming.controller.client;

import com.crowdfunding.farming.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jiang-gege
 * 2020/10/2114:29
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;


}