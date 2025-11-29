package com.osir.catalogservice.mapper;

import com.osir.takeoutpojo.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /**
     * 根据菜品id查询套餐id
     * @param dishId
     * @return
     */
    @Select("select sd.setmeal_id from setmeal_dish sd where sd.dish_id = #{dishId}")
    List<Long> getSetmealIdsByDishId(Long dishId);

    /**
     * 根据菜品id查询菜品是否关联了套餐
     * @param i
     * @return
     */
    @Select("select count(id) from setmeal_dish where dish_id = #{id}")
    int countByDishId(Long i);

    /**
     * 批量插入套餐和菜品的关联关系
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);

    /**
     * 根据套餐id查询套餐和菜品的关联关系
     * @param id
     * @return
     */
    @Select("select dish_id from setmeal_dish where setmeal_id = #{id}")
    List<Long> getDishIdBySetmealId(Long id);

    /**
     * 根据套餐id查询套餐和菜品的关联关系
     * @param id
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> getSetmealDishesBySetmealId(Long id);

    /**
     * 根据套餐id删除套餐和菜品的关联关系
     * @param id
     */
    @Select("delete from setmeal_dish where setmeal_id = #{id}")
    void deleteBySetmealId(Long id);

    /**
     * 根据id批量删除套餐和菜品的关联关系
     * @param ids
     */
    void deleteBatch(List<Long> ids);
}
