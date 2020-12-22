package com.crowdfunding.farming.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Jiang-gege
 * 2020/11/311:19
 */
@Data
@Table(name = "t_region")
public class Region {

    @Id
    @KeySql(useGeneratedKeys=true)
    private Integer id;

    private String regionName;

    private String code;

    private String alpha;

    private String parentCode;

    private Integer beSupported;

}