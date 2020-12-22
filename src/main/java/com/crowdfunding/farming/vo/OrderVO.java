package com.crowdfunding.farming.vo;

import lombok.Data;
import javax.persistence.Transient;

@Data
public class OrderVO {

    private String userId;
    private String buyerMessage;// 买家留言
    private Integer addressId;
    private String  crowdFundingId;
    private Integer num;// 商品购买数量

    @Transient
    private Integer status;


}
