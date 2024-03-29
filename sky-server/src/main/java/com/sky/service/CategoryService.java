package com.sky.service;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {

    /**
     * 根据类型分类，1是菜品，2是套餐
     * @param type
     * @return
     */
    List<Category> getByList(Integer type);

    /**
     * 分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 修改分类
     * @param categoryDTO
     * @return
     */
    void changeInfo(CategoryDTO categoryDTO);

    /**
     * 修改状态
     * @param status
     * @param id
     */
    void startAndStop(Integer status, Long id);

    /**
     * 增加分类
     * @param categoryDTO
     */
    void save(CategoryDTO categoryDTO);

    /**
     * 根据id删除分类
     * @param id
     */
    void deleteById(Long id);
}
