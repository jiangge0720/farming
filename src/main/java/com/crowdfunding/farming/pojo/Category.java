package com.crowdfunding.farming.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Jiang-gege
 * 2020/10/1922:30
 */
@Table(name="t_category")
@Data
public class Category {
    @Id
    @KeySql(useGeneratedKeys=true)
    private Long id;
    private String name;
    private Long parentId;
    private Integer isParent;
    private Integer sort;
}