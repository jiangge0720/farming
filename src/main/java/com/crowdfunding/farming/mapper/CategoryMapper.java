package com.crowdfunding.farming.mapper;

import com.crowdfunding.farming.pojo.Category;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Jiang-gege
 * 2020/10/1922:31
 */
@org.apache.ibatis.annotations.Mapper
public interface CategoryMapper extends Mapper<Category>, IdListMapper<Category,Long> {
}
