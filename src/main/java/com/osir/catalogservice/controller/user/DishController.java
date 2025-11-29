package com.osir.catalogservice.controller.user;

import com.osir.catalogservice.constant.StatusConstant;
import com.osir.catalogservice.service.DishService;
import com.osir.takeoutpojo.entity.Dish;
import com.osir.takeoutpojo.result.Result;
import com.osir.takeoutpojo.vo.DishVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
public class DishController {

    private final DishService dishService;

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @Cacheable(cacheNames = "dishCache" ,key = "#categoryId") // 值为Result.success(list);
    public Result list(@RequestParam("categoryId") Long categoryId) {
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);//查询起售中的菜品

        List<DishVO> list = dishService.listWithFlavor(dish);

        return Result.success(list);
    }

}
