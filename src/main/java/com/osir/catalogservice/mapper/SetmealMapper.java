package com.osir.catalogservice.mapper;

import com.github.pagehelper.Page;
import com.osir.catalogservice.annotation.AutoFill;
import com.osir.catalogservice.enumeration.OperationType;
import com.osir.takeoutpojo.dto.SetmealPageQueryDTO;
import com.osir.takeoutpojo.entity.Setmeal;
import com.osir.takeoutpojo.vo.DishItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    /**
     * 套餐起售停售
     * @param status
     * @param ids
     * @param updateTime
     * @param updateUser
     */
    void updateSetmealStatus(Integer status, List<Long> ids, LocalDateTime updateTime,  Long updateUser);

    /**
     * 新增套餐
     * @param setmeal
     */
    @AutoFill(op= OperationType.INSERT,pos=0)
    void insert(Setmeal setmeal);

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    Page<Setmeal> getPage(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @Select("select * from setmeal where id = #{id}")
    Setmeal getById(Long id);

    /**
     * 修改套餐
     * @param setmeal
     */
    @AutoFill(op= OperationType.UPDATE,pos=0)
    void updateSetmeal(Setmeal setmeal);

    /**
     * 根据id查询套餐状态
     * @param i
     * @return
     */
    @Select("select status from setmeal where id=#{id}")
    Object getStatus(Long i);

    /**
     * 批量删除
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 动态条件查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据套餐id查询菜品选项
     * @param setmealId
     * @return
     */
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);

    /**
     * 根据条件统计套餐数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);
}
