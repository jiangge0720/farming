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
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jiang-gege
 * 2020/6/2417:08
 */
@Slf4j
@Service
public class CrowdFundingServiceImpl implements CrowdFundingService {

  private final IdWorker idWorker;

  private final CrowdFundingMapper crowdFundingMapper;

  private static final List<String> ALLOW_TYPES = Arrays.asList("image/jpeg","image/png");

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
      // 创建查询条件
      CrowdFunding record = new CrowdFunding();
      record.setCreator(userId);
      Page<CrowdFunding> pageInfo = (Page<CrowdFunding>) this.crowdFundingMapper.select(record);

      return new PageResult<>(pageInfo.getTotal(), pageInfo);
    } catch (Exception e) {
      log.error("查询众筹 列表出错", e);
      return null;
    }
  }

  @Override
  public CrowdFunding queryUserCrowdFundingById(String crowdFundingId,Integer userId) {
    // 获取登录用户
    CrowdFunding record = new CrowdFunding();
    record.setId(crowdFundingId);
    record.setCreator(userId);
    CrowdFunding crowdFunding = crowdFundingMapper.selectOne(record);
    return crowdFunding;
  }

  @Override
  public String uploadImage(MultipartFile file) {
    try {
      //校验文件类型
      String contentType = file.getContentType();
      if(!ALLOW_TYPES.contains(contentType)){
        throw new RuntimeException("文件格式不正確");
      }
        String originalFilename = file.getOriginalFilename();
      //准备目标路径
        //File dest = new File("F:/upload/",originalFilename);
      File dest = new File("/home/image/",originalFilename);
      //保存文件到本地
      file.transferTo(dest);

        //返回路径
      return "http://47.98.216.68/image/"+file.getOriginalFilename();
      //return "localhost:8080/"+ originalFilename;
    } catch (IOException e) {
      //上传失败
      throw new RuntimeException("上傳失敗");
    }

  }
}