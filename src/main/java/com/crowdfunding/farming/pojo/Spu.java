package com.crowdfunding.farming.pojo;

import lombok.Data;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * @author Jiang-gege
 * 2020/6/2313:00
 */
@Data
@Table(name = "t_spu")
public class Spu {
    private Long id;

    private String title;

    private String name;

    private String description;

    private Long cid1;// 1级类目

    private Long cid2;// 2级类目

    private Long cid3;// 3级类目

    private String image;

    @Transient
    private String cname;

}