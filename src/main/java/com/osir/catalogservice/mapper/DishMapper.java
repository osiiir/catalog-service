package com.osir.catalogservice.mapper;

import com.github.pagehelper.Page;
import com.osir.catalogservice.annotation.AutoFill;
import com.osir.catalogservice.enumeration.OperationType;
import com.osir.takeoutpojo.dto.DishPageQueryDTO;
import com.osir.takeoutpojo.entity.Dish;
import com.osir.takeoutpojo.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(*) from dish where category_id=#{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 新增菜品
     * @param dish
     */
    @AutoFill(op = OperationType.INSERT,pos=0)
    void insertDish(Dish dish);

    /**
     * 分页查询菜品
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> getPage(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    DishVO getById(Long id);

    /**
     * 根据id查询菜品启售状态
     * @param id
     */
    @Select("select status from dish where id=#{id}")
    Integer getStatus(Long id);

    /**
     * 批量删除菜品
     * @param ids
     */
    void deleteBatch(Long[] ids);

    /**
     * 修改菜品
     * @param dish
     */
    @AutoFill(op = OperationType.UPDATE,pos=0)
    void updateDish(Dish dish);

    /**
     * 根据分类id查询菜品
     * @param dish
     * @return
     */
    List<Dish> list(Dish dish);

    /**
     * 根据条件统计菜品数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);
}
