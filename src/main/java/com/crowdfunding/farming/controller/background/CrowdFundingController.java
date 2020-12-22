package com.crowdfunding.farming.controller.background;

import com.crowdfunding.farming.pojo.CrowdFunding;
import com.crowdfunding.farming.service.CrowdFundingService;
import com.crowdfunding.farming.vo.PageResult;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Jiang-gege
 * 2020/6/2417:14
 */
@RestController
@RequestMapping("crowdFunding")
@Api("众筹服务接口")
public class CrowdFundingController {

    @Resource
    private CrowdFundingService crowdFundingService;

    @PostMapping
    @ApiOperation(value = "创建众筹，返回结果", notes = "创建众筹")
    public ResponseEntity<String> createCrowdFunding(@RequestBody CrowdFunding crowdFunding) {
        String id = crowdFundingService.createCrowdingFunding(crowdFunding);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除众筹，返回结果", notes = "删除众筹")
    public ResponseEntity<Boolean> deleteCrowdFunding(@RequestParam String crowdFundingId){
        return ResponseEntity.ok(crowdFundingService.deleteCrowdFunding(crowdFundingId));
    }

    @GetMapping("list")
    @ApiOperation(value = "分页查询当前用户众筹项目", notes = "分页查询当前用户众筹项目")
    public ResponseEntity<PageResult<CrowdFunding>> queryUserCrowdFundingList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "userId") Integer userId) {
        PageResult<CrowdFunding> result = crowdFundingService.queryUserCrowdFundingList(page, rows, status, userId);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/id")
    @ApiOperation(value = "根据id查询当前用户众筹项目", notes = "根据id查询当前用户众筹项目")
    public ResponseEntity<CrowdFunding> queryUserCrowdFundingById(@RequestParam("crowdFundingId") String crowdFundingId) {
        CrowdFunding result = crowdFundingService.queryUserCrowdFundingById(crowdFundingId);
        return ResponseEntity.ok(result);
    }
}