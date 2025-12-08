package com.osir.catalogservice.service;

import com.osir.takeoutpojo.dto.DishDTO;
import com.osir.takeoutpojo.dto.DishPageQueryDTO;
import com.osir.takeoutpojo.entity.Dish;
import com.osir.takeoutpojo.result.PageResult;
import com.osir.takeoutpojo.vo.DishVO;

import java.util.List;
import java.util.Map;

public interface DishService {
    /**
     * 新增菜品
     * @param dishDTO
     */
    void addDish(DishDTO dishDTO);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    DishVO getById(Long id);

    /**
     * 菜品起售停售
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 删除菜品
     * @param id
     */
    void delete(String id);

    /**
     *  修改菜品
     * @param dishDTO
     */
    void updateDish(DishDTO dishDTO);

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    List<Dish> list(Long categoryId);

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);

    /**
     * 根据条件统计菜品数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);
}
