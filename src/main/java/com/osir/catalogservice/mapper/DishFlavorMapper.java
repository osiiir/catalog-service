package com.osir.catalogservice.mapper;

import com.osir.takeoutpojo.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * 批量插入口味数据
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * 根据菜品id查询菜品口味数据
     * @param id
     * @return
     */
    List<DishFlavor> getByDishId(Long id);


    /**
     * 根据id批量删除菜品口吻
     * @param ids
     */
    void deleteBatch(Long[] ids);
}
