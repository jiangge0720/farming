package com.crowdfunding.farming.controller.client;

import com.crowdfunding.farming.pojo.Category;
import com.crowdfunding.farming.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Jiang-gege
 * 2020/10/1922:33
 */
@RestController
@RequestMapping("category")
@Api("分类服务接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据父节点id查询商品分类
     *
     * @param pid
     * @return
     */
    @GetMapping("list")
    @ApiOperation(value = "根据父节点id查询商品分类，返回分类对象", notes = "根据父节点id查询商品分类")
    public ResponseEntity<List<Category>> queryCategoryListByPid(@RequestParam("pid") Long pid) {

        return ResponseEntity.ok(categoryService.queryCategoryListByPid(pid));
    }

    /**
     * 根据id查询商品分类
     *
     * @param ids
     * @return
     */
    @GetMapping("list/ids")
    @ApiOperation(value = "根据id查询商品分类，返回分类对象", notes = "根据id查询商品分类")
    public ResponseEntity<List<Category>> queryCategoryByIds(@RequestParam("ids") List<Long> ids) {
        return ResponseEntity.ok(categoryService.queryByIds(ids));
    }
    /**
     * 新增分类
     *
     * @param category
     * @return
     */
    @PostMapping
    @ApiOperation(value = "新增分类，返回结果", notes = "新增分类")
    public ResponseEntity<Boolean> insertCategory(@RequestBody Category category) {

        return ResponseEntity.ok(categoryService.insertCategory(category));
    }
        /**
         * 删除分类
         *
         * @param categoryId
         * @return
         */
    @PutMapping
    @ApiOperation(value = "删除分类，返回结果", notes = "删除分类")
    public ResponseEntity<Boolean> deleteCategory(@RequestParam("categoryId") Long categoryId){
        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
        }
    }
