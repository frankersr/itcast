package com.sky.controller.admin;


import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.*;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分类管理
 */
@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类管理接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    /**
     * 根据类型分类，1是菜品，2是套餐
     * @param type
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据类型分类")
    public Result<List<Category>> getByList(Integer type){
       List<Category>  category=  categoryService.getByList(type);
        return Result.success(category);

    }


    /**
     * 分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO){

        PageResult p=categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(p);

    }


    /**
     * 修改分类
     * @param categoryDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改分类")
    public Result changeInfo(@RequestBody CategoryDTO categoryDTO){

        categoryService.changeInfo(categoryDTO);
        return Result.success();
    }


    /**
     * 分类启用与禁用
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("分类状态修改")
    public Result startAndStop(@PathVariable Integer status,Long id) {

        categoryService.startAndStop(status, id);
        return Result.success();
    }


    /**
     * 增加分类
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @ApiOperation("增加分类")
    public Result save(@RequestBody CategoryDTO categoryDTO){
        categoryService.save(categoryDTO);
        return Result.success();
    }


    /**
     * 删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("删除分类")
    public Result<String> deleteById(Long id){
        log.info("删除分类：{}", id);
        categoryService.deleteById(id);
        return Result.success();
    }





    }
