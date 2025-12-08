package com.osir.catalogservice.controller.user;

import com.osir.catalogservice.constant.StatusConstant;
import com.osir.catalogservice.service.SetmealService;
import com.osir.takeoutpojo.entity.Setmeal;
import com.osir.takeoutpojo.result.Result;
import com.osir.takeoutpojo.vo.DishItemVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController("userSetmealController")
@RequestMapping("/catalog/user/setmeal")
public class SetmealController {

    private final SetmealService setmealService;

    /**
     * 条件查询
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @Cacheable(cacheNames = "setmealCache",key = "#categoryId")
    public Result list(@RequestParam("categoryId") Long categoryId) {
        Setmeal setmeal = new Setmeal();
        setmeal.setCategoryId(categoryId);
        setmeal.setStatus(StatusConstant.ENABLE);

        List<Setmeal> list = setmealService.list(setmeal);
        return Result.success(list);
    }

    /**
     * 根据套餐id查询包含的菜品列表
     * @param id
     * @return
     */
    @GetMapping("/dish/{id}")
    @Cacheable(cacheNames = "setmealDishCache",key = "#id")
    public Result dishList(@PathVariable("id") Long id) {
        List<DishItemVO> list = setmealService.getDishItemById(id);
        return Result.success(list);
    }
}
