package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 根据类型分类，1是菜品，2是套餐
     * @param type
     * @return
     */
    @Select("select * from sky_take_out.category where type=#{type}")
    List<Category> getByList(Integer type);


    /**
     * 分页查询
     * @param categoryPageQueryDTO
     * @return
     */

    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 修改分类
     * @param category
     * @return
     */
    void update(Category category);

    /**
     * 增加分类
     * @param category
     * @return
     */
    @Insert("insert into category(type, name, sort, status, create_time, update_time, create_user, update_user)" +
            " VALUES" +
            " (#{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insert(Category category);


    /**
     * 删除分类
     * @param id
     * @return
     */
    @Delete("delete from category where id=#{id}")
    void delete(Long id);
}
