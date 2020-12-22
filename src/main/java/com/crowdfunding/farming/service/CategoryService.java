package com.crowdfunding.farming.service;

import com.crowdfunding.farming.pojo.Category;

import java.util.List;

/**
 * @author Jiang-gege
 * 2020/10/1922:31
 */
public interface CategoryService  {
    List<Category> queryCategoryListByPid(Long pid);

    List<Category> queryByIds(List<Long> ids);

    Boolean insertCategory(Category category);

    Boolean deleteCategory(Long categoryId);
}
