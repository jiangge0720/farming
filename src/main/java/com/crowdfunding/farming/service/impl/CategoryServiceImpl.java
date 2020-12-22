package com.crowdfunding.farming.service.impl;

import com.crowdfunding.farming.mapper.CategoryMapper;
import com.crowdfunding.farming.pojo.Category;
import com.crowdfunding.farming.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jiang-gege
 * 2020/10/1922:32
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> queryCategoryListByPid(Long pid){
        //查询条件
        Category t = new Category();
        t.setParentId(pid);
        List<Category> list = categoryMapper.select(t);
        return list;
    }

    @Override
    public List<Category> queryByIds(List<Long>ids){
        List<Category> list = categoryMapper.selectByIdList(ids);
        return list;
    }

    @Override
    public Boolean insertCategory(Category category) {
        category.setId(null);
        int result = categoryMapper.insert(category);
        return result == 1;
    }

    @Override
    public Boolean deleteCategory(Long categoryId) {
        Category record = new Category();
        record.setId(categoryId);
        int result = categoryMapper.delete(record);
        return result == 1;
    }
}