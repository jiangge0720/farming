package com.crowdfunding.farming.pojo;

import lombok.Data;

import javax.persistence.Table;

/**
 * @author Jiang-gege
 * 2020/10/1413:01
 */
@Data
@Table(name = "t_address")
public class Address {
    private Integer addressId;

    private Integer userId;

    private String receiver;

    private String phone;

    private String receiverState;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverAddress;


}