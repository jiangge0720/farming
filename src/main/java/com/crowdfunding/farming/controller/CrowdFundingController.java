package com.crowdfunding.farming.controller;

import com.crowdfunding.farming.pojo.CrowdFunding;
import com.crowdfunding.farming.pojo.Order;
import com.crowdfunding.farming.service.CrowdFundingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jiang-gege
 * 2020/6/2417:14
 */
@RestController
@RequestMapping("crowdFunding")
public class CrowdFundingController {

    @Autowired
    private CrowdFundingService crowdFundingService;

    @PostMapping
    public ResponseEntity<Long> createOrder(@RequestBody CrowdFunding crowdFunding) {
        Long id = this.crowdFundingService.createCrowdingFunding(crowdFunding);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }
}