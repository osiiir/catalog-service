package com.osir.catalogservice.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.osir.catalogservice.constant.StatusConstant;
import com.osir.catalogservice.exception.DeletionNotAllowedException;
import com.osir.catalogservice.mapper.DishFlavorMapper;
import com.osir.catalogservice.mapper.DishMapper;
import com.osir.catalogservice.mapper.SetmealDishMapper;
import com.osir.catalogservice.mapper.SetmealMapper;
import com.osir.catalogservice.service.DishService;
import com.osir.commonservice.utils.LoginUserContext;
import com.osir.takeoutpojo.constant.ErrorMessageConstant;
import com.osir.takeoutpojo.dto.DishDTO;
import com.osir.takeoutpojo.dto.DishPageQueryDTO;
import com.osir.takeoutpojo.entity.Dish;
import com.osir.takeoutpojo.entity.DishFlavor;
import com.osir.takeoutpojo.result.PageResult;
import com.osir.takeoutpojo.vo.DishVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishMapper dishMapper;
    private final DishFlavorMapper dishFlavorMapper;
    private final SetmealMapper setmealMapper;
    private final SetmealDishMapper setmealDishMapper;

    /**
     * 增加菜品
     * @param dishDTO
     * @return
     */
    // 这里操作了多张表，需要添加事务，确保数据库正确操作
    @Transactional
    public void addDish(DishDTO dishDTO){
        /*File file = new File("C:\\Users\\24151\\Desktop\\test\\ICPC.png");
        String url = aliOssUtil.upload(Files.readAllBytes(file.toPath()), "ICPC.png");*/
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        dish.setStatus(StatusConstant.DISABLE);
        dishMapper.insertDish(dish);

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(dish.getId()));
            dishFlavorMapper.insertBatch(flavors);
        }

    }

    /**
     * 分页查询菜品
     * @param dishPageQueryDTO
     * @return
     */
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());

        Page<DishVO> page = dishMapper.getPage(dishPageQueryDTO);

        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 根据id查询菜品及其口味数据
     * @param id
     * @return
     */
    public DishVO getById(Long id) {
        DishVO dishVO = dishMapper.getById(id);
        List<DishFlavor> flavors = dishFlavorMapper.getByDishId(id);
        dishVO.setFlavors(flavors);
        return dishVO;
    }


    /**
     * 菜品起售停售
     * @param status
     * @param id
     */
    @Transactional
    public void startOrStop(Integer status, Long id) {
        if(Objects.equals(status, StatusConstant.DISABLE)){
            List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishId(id);
            if (setmealIds != null && !setmealIds.isEmpty()) {
                setmealMapper.updateSetmealStatus(StatusConstant.DISABLE, setmealIds, LocalDateTime.now(), LoginUserContext.getUserId());
            }
        }
        Dish dish = new Dish();
        dish.setStatus(status);
        dish.setId(id);
        dishMapper.updateDish(dish);
    }

    /**
     * 删除菜品
     * @param id
     */
    @Transactional
    public void delete(String id) {
        String[] split = id.split(",");
        Long[] ids = new Long[split.length];
        for (int i = 0; i < split.length; i++) {
            ids[i] = Long.valueOf(split[i]);
        }
        for(Long i : ids){
            if(Objects.equals(dishMapper.getStatus(i), StatusConstant.ENABLE)){
                   throw new DeletionNotAllowedException(ErrorMessageConstant.DISH_ON_SALE);
            }
            if(setmealDishMapper.countByDishId(i) > 0){
                throw new DeletionNotAllowedException(ErrorMessageConstant.DISH_BE_RELATED_BY_SETMEAL);
            }
        }
        dishMapper.deleteBatch(ids);
        dishFlavorMapper.deleteBatch(ids);
    }

    /**
     * 修改菜品
     * @param dishDTO
     */
    @Transactional
    public void updateDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null) {
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(dish.getId()));
            Long[] ids = new Long[1];
            ids[0]=dish.getId();
            dishFlavorMapper.deleteBatch(ids);
            if(!flavors.isEmpty()) dishFlavorMapper.insertBatch(flavors);
        }
        dishMapper.updateDish(dish);
    }

    /**
     * 获取菜品列表
     * @param categoryId
     * @return
     */
    public List<Dish> list(Long categoryId) {
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);
        List<Dish> list = dishMapper.list(dish);
        return list;
    }

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.list(dish);

        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishFlavorMapper.getByDishId(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }

        return dishVOList;
    }

    @Override
    public Integer countByMap(Map map) {
        return dishMapper.countByMap(map);
    }

}
