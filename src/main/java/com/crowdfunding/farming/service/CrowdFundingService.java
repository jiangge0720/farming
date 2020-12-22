package com.crowdfunding.farming.service;

import com.crowdfunding.farming.pojo.CrowdFunding;
import com.crowdfunding.farming.vo.PageResult;

/**
 * @author: Kevin
 * @date: 2020/6/27
 * @time: 上午1:21
 * @description:
 **/

public interface CrowdFundingService {

  /**
   * create
   *
   * @param crowdFunding
   * @return
   */
  String createCrowdingFunding(CrowdFunding crowdFunding);

  Boolean deleteCrowdFunding(String crowdFundingId);

  PageResult<CrowdFunding> queryUserCrowdFundingList(Integer page, Integer rows, Integer status, Integer userId);

  CrowdFunding queryUserCrowdFundingById(String crowdFundingId);
}
