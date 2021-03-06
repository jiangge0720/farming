package com.crowdfunding.farming.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Data
@Table(name = "t_order")
public class Order {

    @Id
    private Long orderId;// id
    private BigDecimal totalPay;// 总金额
    private Integer postFee;// 邮费
    private Date createTime;// 创建时间
    private String shippingName;// 物流名称
    private String shippingCode;// 物流单号
    private String userId;// 用户id
    private String buyerMessage;// 买家留言
    private String buyerNick;// 买家昵称
    private Integer buyerRate;// 买家是否已经评价
    private String receiver; // 收货人全名
    private String receiverMobile; // 移动电话
    private String receiverState; // 省份
    private String receiverCity; // 城市
    private String receiverDistrict; // 区/县
    private String receiverAddress; // 收货地址，如：xx路xx号
    private String receiverZip; // 邮政编码,如：310001
    private Integer invoiceType;// 发票类型，0无发票，1普通发票，2电子发票，3增值税发票
    private Integer sourceType;// 订单来源 1:app端，2：pc端，3：M端，4：微信端，5：手机qq端
    private String  crowdFundingId;

    @Transient
    private OrderDetail orderDetail;


    @Transient
    private Integer status;


}
