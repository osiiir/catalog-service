package com.osir.catalogservice.controller.admin;

import com.osir.catalogservice.service.SetmealService;
import com.osir.takeoutpojo.dto.SetmealDTO;
import com.osir.takeoutpojo.dto.SetmealPageQueryDTO;
import com.osir.takeoutpojo.result.PageResult;
import com.osir.takeoutpojo.result.Result;
import com.osir.takeoutpojo.vo.SetmealVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/setmeal")
public class SetmealController {

    private final SetmealService setmealService;

    /**
     * 套餐起售停售
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result startOrStop(@PathVariable Integer status, Long id) {
        log.info("批量起售停售：{}", id);
        setmealService.startOrStop(status, id);
        return null;
    }

    /**
     * 添加套餐
     * @param setmealDTO
     * @return
     */
    @PostMapping
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result addSetmeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐：{}", setmealDTO);
        setmealService.addSetmeal(setmealDTO);
        return Result.success();
    }

    /**
     *  套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result page(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("分页查询套餐：{}", setmealPageQueryDTO);
        PageResult pageResult = setmealService.getPage(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        log.info("查询套餐：{}", id);
        SetmealVO setmealVO = setmealService.getById(id);
        return Result.success(setmealVO);
    }

    /**
     * 修改套餐
     * @param setmealDTO
     * @return
     */
    @PutMapping
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result updateSetmeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("修改套餐：{}", setmealDTO);
        setmealService.updateSetmeal(setmealDTO);
        return Result.success();
    }

    /**
     * 批量删除套餐
     * @param id
     * @return
     */
    @DeleteMapping
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result deleteBatch(@RequestParam("ids") String id) {
        log.info("批量删除套餐：{}", id);
        setmealService.deleteBatch(id);
        return Result.success();
    }

}
