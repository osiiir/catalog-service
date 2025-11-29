package com.osir.catalogservice.service;


import com.osir.takeoutpojo.dto.SetmealDTO;
import com.osir.takeoutpojo.dto.SetmealPageQueryDTO;
import com.osir.takeoutpojo.entity.Setmeal;
import com.osir.takeoutpojo.result.PageResult;
import com.osir.takeoutpojo.vo.DishItemVO;
import com.osir.takeoutpojo.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    /**
     * 套餐起售停售
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 新增套餐
     * @param setmealDTO
     */
    void addSetmeal(SetmealDTO setmealDTO);

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult getPage(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    SetmealVO getById(Long id);

    /**
     * 修改套餐
     * @param setmealDTO
     */
    void updateSetmeal(SetmealDTO setmealDTO);

    /**
     * 批量删除套餐
     * @param id
     */
    void deleteBatch(String id);

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);

}
