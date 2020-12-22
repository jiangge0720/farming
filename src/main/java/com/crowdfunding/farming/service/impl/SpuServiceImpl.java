package com.crowdfunding.farming.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.crowdfunding.farming.mapper.SpuMapper;
import com.crowdfunding.farming.pojo.Category;
import com.crowdfunding.farming.pojo.Spu;
import com.crowdfunding.farming.service.CategoryService;
import com.crowdfunding.farming.service.SpuService;
import com.crowdfunding.farming.vo.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jiang-gege
 * 2020/10/1816:33
 */
@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryService categoryService;

    @Override
    public PageResult<Spu> querySpuList(Integer page, Integer rows, String key) {
        //分页
        PageHelper.startPage(page,rows);

        //过滤
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        //搜索过滤字段
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("titile","%"+key+"%");
        }
        //默认排序
        //查询
        List<Spu> spus = spuMapper.selectByExample(example);

        //解析分类和品牌的名称
        loadCategoryAndBrandName(spus);
        //解析分页结果
        PageInfo<Spu> info = new PageInfo<>(spus);
        return new PageResult<>(info.getTotal(), spus);
    }

    @Override
    public int saveGoods(Spu spu) {
        spu.setId(null);
        int count = spuMapper.insert(spu);
        return count;
    }

    @Override
    public Spu querySpuById(Long id) {
        //查询spu
        Spu record = new Spu();
        record.setId(id);
        return spuMapper.selectOne(record);
    }

    @Override
    public int modifyGoods(Spu spu) {
        //查询id
        Long spuId = spu.getId();
        Spu record = new Spu();
        record.setId(spuId);
        Spu data = spuMapper.selectOne(record);
        if(data != null){
            //删除原来的
            spuMapper.delete(data);
            //新增
            int result = spuMapper.insert(spu);
            return result;
        }
        return 0;
    }

    @Override
    public int deleteSpu(Long spuId) {
        Spu record = new Spu();
        record.setId(spuId);
        return spuMapper.delete(record);
    }

    private void loadCategoryAndBrandName(List<Spu> spus) {
        for (Spu spu : spus) {
            //处理分类名称
            List<String> names = categoryService.queryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                    .stream().map(Category::getName).collect(Collectors.toList());
            spu.setCname(StringUtils.join(names, "/"));
        }
    }
}