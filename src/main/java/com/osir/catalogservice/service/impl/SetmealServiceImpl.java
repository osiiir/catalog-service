package com.osir.catalogservice.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.osir.catalogservice.constant.StatusConstant;
import com.osir.catalogservice.exception.DeletionNotAllowedException;
import com.osir.catalogservice.mapper.DishMapper;
import com.osir.catalogservice.mapper.SetmealDishMapper;
import com.osir.catalogservice.mapper.SetmealMapper;
import com.osir.catalogservice.service.SetmealService;
import com.osir.takeoutpojo.constant.ErrorMessageConstant;
import com.osir.takeoutpojo.dto.SetmealDTO;
import com.osir.takeoutpojo.dto.SetmealPageQueryDTO;
import com.osir.takeoutpojo.entity.Setmeal;
import com.osir.takeoutpojo.entity.SetmealDish;
import com.osir.takeoutpojo.result.PageResult;
import com.osir.takeoutpojo.vo.DishItemVO;
import com.osir.takeoutpojo.vo.SetmealVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SetmealServiceImpl implements SetmealService {

    private final SetmealMapper setmealMapper;
    private final SetmealDishMapper setmealDishMapper;
    private final DishMapper dishMapper;

    /**
     * 启停售套餐
     * @param status
     * @param id
     */
    public void startOrStop(Integer status, Long id) {
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        if(Objects.equals(status, StatusConstant.ENABLE)){
            List<Long> dishIds = setmealDishMapper.getDishIdBySetmealId(id);
            if(dishIds!=null && !dishIds.isEmpty()){
                for(Long dishId : dishIds){
                    Integer sta = dishMapper.getStatus(dishId);
                    if(Objects.equals(sta, StatusConstant.DISABLE)){
                        throw new DeletionNotAllowedException(ErrorMessageConstant.SETMEAL_ENABLE_FAILED);
                    }
                }
            }
        }
        //TODO Need UserId
        setmealMapper.updateSetmealStatus(status, ids, LocalDateTime.now(), 0L/*BaseContext.getCurrentId()*/);
    }

    /**
     * 添加套餐
     * @param setmealDTO
     */
    @Transactional
    public void addSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmeal.setStatus(StatusConstant.DISABLE);
        setmealMapper.insert(setmeal);

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if(setmealDishes != null){
            setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmeal.getId()));
            setmealDishMapper.insertBatch(setmealDishes);
        }

    }

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    public PageResult getPage(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<Setmeal> page = setmealMapper.getPage(setmealPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    public SetmealVO getById(Long id) {
        SetmealVO setmealVO = new SetmealVO();
        Setmeal setmeal = setmealMapper.getById(id);
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishMapper.getSetmealDishesBySetmealId(id));
        return setmealVO;
    }

    /**
     * 修改套餐
     * @param setmealDTO
     */
    @Transactional
    public void updateSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.updateSetmeal(setmeal);
        setmealDishMapper.deleteBySetmealId(setmeal.getId());
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if(setmealDishes != null){
            setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmeal.getId()));
            setmealDishMapper.insertBatch(setmealDishes);
        }
    }

    /**
     * 批量删除套餐
     * @param id
     */
    @Transactional
    public void deleteBatch(String id) {
        List<Long> ids = new ArrayList<>();
        String[] s = id.split(",");
        for(String i : s){
            ids.add(Long.valueOf(i));
        }
        for(Long i : ids){
            if(Objects.equals(setmealMapper.getStatus(i), StatusConstant.ENABLE)){
                throw new DeletionNotAllowedException(ErrorMessageConstant.SETMEAL_ON_SALE);
            }
        }
        setmealMapper.deleteBatch(ids);
        setmealDishMapper.deleteBatch(ids);
    }

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }

}
