package com.crowdfunding.farming.service.impl;

import com.crowdfunding.farming.interceptor.LoginInterceptor;
import com.crowdfunding.farming.mapper.CrowdFundingMapper;
import com.crowdfunding.farming.pojo.CrowdFunding;
import com.crowdfunding.farming.pojo.UserInfo;
import com.crowdfunding.farming.service.CrowdFundingService;
import com.crowdfunding.farming.utils.IdWorker;
import com.crowdfunding.farming.vo.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author Jiang-gege
 * 2020/6/2417:08
 */
@Slf4j
@Service
public class CrowdFundingServiceImpl implements CrowdFundingService {

  private final IdWorker idWorker;

  private final CrowdFundingMapper crowdFundingMapper;

  public CrowdFundingServiceImpl(IdWorker idWorker,
                                 CrowdFundingMapper crowdFundingMapper) {
    this.idWorker = idWorker;
    this.crowdFundingMapper = crowdFundingMapper;
  }

  @Override
  public String createCrowdingFunding(CrowdFunding crowdFunding) {

    UserInfo user = LoginInterceptor.getUserInfo();
    String crowdFundingId = String.valueOf(idWorker.nextId());
    crowdFunding.setId(crowdFundingId);
    crowdFunding.setSell(0);
    crowdFunding.setStatus(0);
    crowdFunding.setCreator(user.getId());
    crowdFundingMapper.insertSelective(crowdFunding);
    return crowdFundingId;
  }

  @Override
  public Boolean deleteCrowdFunding(String crowdFundingId) {
    CrowdFunding record = new CrowdFunding();
    record.setId(crowdFundingId);
    int result = crowdFundingMapper.delete(record);
    return result == 1;
  }

  @Override
  public PageResult<CrowdFunding> queryUserCrowdFundingList(Integer page, Integer rows, Integer status, Integer userId) {
    try {
      // 分页
      PageHelper.startPage(page, rows);
      // 获取登录用户
      UserInfo user = LoginInterceptor.getUserInfo();

      // 创建查询条件
      CrowdFunding record = new CrowdFunding();
      record.setCreator(user.getId());
      Page<CrowdFunding> pageInfo = (Page<CrowdFunding>) this.crowdFundingMapper.select(record);

      return new PageResult<>(pageInfo.getTotal(), pageInfo);
    } catch (Exception e) {
      log.error("查询众筹 列表出错", e);
      return null;
    }
  }

  @Override
  public CrowdFunding queryUserCrowdFundingById(String crowdFundingId) {
    // 获取登录用户
    UserInfo user = LoginInterceptor.getUserInfo();
    CrowdFunding record = new CrowdFunding();
    record.setId(crowdFundingId);
    record.setCreator(user.getId());
    CrowdFunding crowdFunding = crowdFundingMapper.selectOne(record);
    return crowdFunding;
  }
}