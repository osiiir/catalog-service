package com.osir.catalogservice.controller.admin;


import com.osir.catalogservice.service.DishService;
import com.osir.takeoutpojo.dto.DishDTO;
import com.osir.takeoutpojo.dto.DishPageQueryDTO;
import com.osir.takeoutpojo.entity.Dish;
import com.osir.takeoutpojo.result.PageResult;
import com.osir.takeoutpojo.result.Result;
import com.osir.takeoutpojo.vo.DishVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/dish")
public class DishController {

    private final DishService dishService;

    /**
     * 增加菜品
     * @param dishDTO
     * @return
     */
    @PostMapping
    @CacheEvict(cacheNames = "dishCache",allEntries = true)
    public Result addDish(@RequestBody DishDTO dishDTO){
        log.info("增加菜品：{}",dishDTO);
        dishService.addDish(dishDTO);
        return Result.success();
    }

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result page(@RequestBody DishPageQueryDTO dishPageQueryDTO){
        log.info("分页查询:{}",dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id){
        log.info("根据id查询菜品：{}",id);
        DishVO dishVO = dishService.getById(id);
        return Result.success(dishVO);
    }

    /**
     * 菜品起售停售
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @CacheEvict(cacheNames = "dishCache",allEntries = true)
    public Result startOrStop(@PathVariable Integer status,Long id){
        log.info("菜品起售停售：sta:{},id:{}",status,id);
        dishService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * 批量删除菜品
     * @param id
     * @return
     */
    @DeleteMapping
    @CacheEvict(cacheNames = "dishCache",allEntries = true)
    public Result delete(@RequestParam("ids")String id){
        log.info("删除菜品：{}",id);
        dishService.delete(id);
        return Result.success();
    }

    /**
     *  修改菜品
     * @param dishDTO
     * @return
     */
    @PutMapping
    @CacheEvict(cacheNames = "dishCache",allEntries = true)
    public Result update(@RequestBody DishDTO dishDTO){
        log.info("修改菜品：{}",dishDTO);
        dishService.updateDish(dishDTO);
        return Result.success();
    }

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    public Result list(@RequestParam("categoryId") Long categoryId){
        log.info("根据分类id查询菜品：{}",categoryId);
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

}
