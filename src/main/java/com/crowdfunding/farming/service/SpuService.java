package com.crowdfunding.farming.service;

import com.crowdfunding.farming.pojo.Spu;
import com.crowdfunding.farming.vo.PageResult;

/**
 * @author Jiang-gege
 * 2020/10/1816:33
 */
public interface SpuService {
    PageResult<Spu> querySpuList(Integer page, Integer rows,String key);

    int saveGoods(Spu spu);

    Spu querySpuById(Long id);

    int modifyGoods(Spu spu);

    int deleteSpu(Long spuId);
}
