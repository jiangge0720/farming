package com.crowdfunding.farming.service.impl;

import com.crowdfunding.farming.interceptor.LoginInterceptor;
import com.crowdfunding.farming.mapper.*;
import com.crowdfunding.farming.pojo.*;
import com.crowdfunding.farming.service.OrderService;
import com.crowdfunding.farming.utils.IdWorker;
import com.crowdfunding.farming.utils.JsonUtils;
import com.crowdfunding.farming.vo.OrderVO;
import com.crowdfunding.farming.vo.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jiang-gege
 * 2020/6/2312:36
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

  private final IdWorker idWorker;

  private final OrderMapper orderMapper;

  private final OrderDetailMapper detailMapper;

  private final OrderStatusMapper statusMapper;

  private final CrowdFundingMapper crowdFundingMapper;

  private final UserMapper userMapper;

  private final AddressMapper addressMapper;

  private final SpuMapper spuMapper;
  @Autowired
  public OrderServiceImpl(CrowdFundingMapper crowdFundingMapper,
                          IdWorker idWorker,
                          OrderMapper orderMapper,
                          OrderDetailMapper detailMapper,
                          OrderStatusMapper statusMapper,
                          UserMapper userMapper,
                          AddressMapper addressMapper,
                          SpuMapper spuMapper) {
    this.crowdFundingMapper = crowdFundingMapper;
    this.idWorker = idWorker;
    this.orderMapper = orderMapper;
    this.detailMapper = detailMapper;
    this.statusMapper = statusMapper;
    this.userMapper = userMapper;
    this.addressMapper = addressMapper;
    this.spuMapper = spuMapper;
  }

  @Override
  @Transactional
  public Long createOrder(OrderVO orderVo) {
    // 生成orderId
    long orderId = idWorker.nextId();
    // 校验用户 todo
    CrowdFunding record = new CrowdFunding();
    record.setId(orderVo.getCrowdFundingId());
    CrowdFunding crowdFunding = crowdFundingMapper.selectOne(record);

    User userRecord = new User();
    userRecord.setNickName(orderVo.getUserId());
    User user = userMapper.selectOne(userRecord);

    Address addressRecord = new Address();
    addressRecord.setAddressId(orderVo.getAddressId());
    Address address = addressMapper.selectOne(addressRecord);

    Spu spuRecord = new Spu();
    spuRecord.setId(crowdFunding.getGoodsId());
    Spu spu = spuMapper.selectOne(spuRecord);

    Order order = new Order();
    order.setOrderId(orderId);

    if(crowdFunding.getStatus() == 1){
      throw new RuntimeException("当前众筹已经完成，无法下单");
    }

    order.setTotalPay(BigDecimal.valueOf(crowdFunding.getPrice().intValue()*orderVo.getNum().intValue()));
    order.setPostFee(5);
    order.setCreateTime(new Date());
    //物流名称
    //物流单号
    order.setUserId(orderVo.getUserId());
    order.setBuyerMessage(orderVo.getBuyerMessage());
    order.setBuyerNick(user.getNickName());
    order.setBuyerRate(0);
    order.setReceiverState(address.getReceiverState());
    order.setReceiverCity(address.getReceiverCity());
    order.setReceiverDistrict(address.getReceiverDistrict());
    order.setReceiverAddress(address.getReceiverAddress());
    order.setReceiverMobile(address.getPhone());
    //收货人邮编
    order.setReceiver(address.getReceiver());
    //发票类型
    //订单来源
    order.setCrowdFundingId(orderVo.getCrowdFundingId());


    // 保存数据
    this.orderMapper.insertSelective(order);

    // 保存订单状态
    OrderStatus orderStatus = new OrderStatus();
    orderStatus.setOrderId(orderId);
    orderStatus.setCreateTime(order.getCreateTime());
    orderStatus.setStatus(1);// 初始状态为未付款

    this.statusMapper.insertSelective(orderStatus);

    // 订单详情中添加orderId
    OrderDetail orderDetail = new OrderDetail();
    orderDetail.setOrderId(orderId);
    orderDetail.setSkuId(spu.getId());
    orderDetail.setNum(orderVo.getNum());
    orderDetail.setTitle(spu.getTitle());
    orderDetail.setPrice(crowdFunding.getPrice());
    orderDetail.setImage(crowdFunding.getImage());
    this.detailMapper.insert(orderDetail);

    log.debug("生成订单，订单编号：{}，用户id：{}", orderId, user.getUserId());

    //判断购买数量
    int total = orderVo.getNum();
    int residue = crowdFunding.getTotal() - crowdFunding.getSell();
    String users = crowdFunding.getUsers();

    if (total > residue) {
      throw new RuntimeException("剩余数量不足");
    } else {
      //添加售卖数量
      crowdFunding.setSell(crowdFunding.getSell() + total);
      if (users.isEmpty()) {
        List<String> usersList = new ArrayList<>();
        usersList.add(orderVo.getUserId());
        String result = JsonUtils.serialize(usersList);
        crowdFunding.setUsers(result);
      } else {
        int flag = 0;
        List<String> result = JsonUtils.parseList(users, String.class);
        for (String jsonResult : result) {
          if (jsonResult.equals(orderVo.getUserId()) ){
            flag = flag + 1;
            break;
          }
        }
        if (flag == 0) {
          result.add(orderVo.getUserId());
          String userList = JsonUtils.serialize(result);
          crowdFunding.setUsers(userList);
        }
      }
    }
    if (residue == total) {
      crowdFunding.setStatus(1);
    }
    CrowdFunding crowdFunding1 = new CrowdFunding();
    crowdFunding1.setId(crowdFunding.getId());
    crowdFundingMapper.delete(crowdFunding1);
    crowdFundingMapper.insertSelective(crowdFunding);
    return orderId;
  }

  @Override
  public Order queryById(Long id) {
    // 查询订单
    Order order = this.orderMapper.selectByPrimaryKey(id);
    // 查询订单详情
    OrderDetail record = new OrderDetail();
    record.setOrderId(id);
    OrderDetail orderDetail = this.detailMapper.selectOne(record);

    order.setOrderDetail(orderDetail);
    // 查询订单状态
    OrderStatus status = this.statusMapper.selectByPrimaryKey(order.getOrderId());

    order.setStatus(status.getStatus());
    return order;
  }

  @Override
  public PageResult<Order> queryUserOrderList(Integer page, Integer rows, Integer status, Integer userId) {
    try {
      // 分页
      PageHelper.startPage(page, rows);
      // 获取登录用户
      UserInfo user = LoginInterceptor.getUserInfo();
      // 创建查询条件
      Page<Order> pageInfo = (Page<Order>) this.orderMapper.queryOrderList(user.getId(), status);

      return new PageResult<>(pageInfo.getTotal(), pageInfo);
    } catch (Exception e) {
      log.error("查询订单出错", e);
      return null;
    }
  }

  @Override
  @Transactional
  public Boolean updateStatus(Long id, Integer status) {
    OrderStatus record = new OrderStatus();
    record.setOrderId(id);
    record.setStatus(status);
    // 根据状态判断要修改的时间
    switch (status) {
      case 2:
        record.setPaymentTime(new Date());// 付款
        break;
      case 3:
        record.setConsignTime(new Date());// 发货
        break;
      case 4:
        record.setEndTime(new Date());// 确认收获，订单结束
        break;
      case 5:
        record.setCloseTime(new Date());// 交易失败，订单关闭
        break;
      case 6:
        record.setCommentTime(new Date());// 评价时间
        break;
      default:
        return null;
    }
    int count = this.statusMapper.updateByPrimaryKeySelective(record);
    return count == 1;
  }

}
