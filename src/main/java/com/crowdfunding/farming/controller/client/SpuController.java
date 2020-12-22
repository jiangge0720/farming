package com.crowdfunding.farming.controller.client;

import com.crowdfunding.farming.pojo.Spu;
import com.crowdfunding.farming.service.SpuService;
import com.crowdfunding.farming.vo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jiang-gege
 * 2020/10/1816:34
 */
@RestController
@RequestMapping("spu")
@Api("商品服务接口")
public class SpuController {

    @Autowired
    private SpuService spuService;

    @GetMapping("list")
    @ApiOperation(value = "分页查询所有商品，并且可以根据上下架以及关键字过滤", notes = "分页查询所有商品")
    public ResponseEntity<PageResult<Spu>> querySpuList(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "row" ,defaultValue = "5") Integer rows,
            @RequestParam(value = "key",required = false) String key){
        PageResult<Spu> result = spuService.querySpuList(page,rows,key);
        if(result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 商品新增
     *
     * @param spu
     * @return
     */
    @PostMapping
    @ApiOperation(value = "新增商品，返回结果", notes = "新增商品")
    public ResponseEntity<Integer> saveGoods(@RequestBody Spu spu){
        return ResponseEntity.ok(spuService.saveGoods(spu));
    }

    /**
     * 根据spu的id查询spu
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    @ApiOperation(value = "根据spu的id查询spu，返回spu对象", notes = "根据spu的id查询spu")
    public ResponseEntity<Spu> querySpuById(@PathVariable("id") Long id){
        return ResponseEntity.ok(spuService.querySpuById(id));
    }

    /**
     * 商品修改
     *
     * @param spu
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改商品，返回结果", notes = "修改商品")
    public ResponseEntity<Integer> modifyGoods(@RequestBody Spu spu){
        return ResponseEntity.ok(spuService.modifyGoods(spu));
    }
    /**
     * 商品删除
     *
     * @param spuId
     * @return
     */
    @GetMapping("delete")
    @ApiOperation(value = "删除商品，返回结果", notes = "删除商品")
    public ResponseEntity<Integer> deleteSpu(@RequestParam("spuId") Long spuId){
        return ResponseEntity.ok(spuService.deleteSpu(spuId));
    }
}