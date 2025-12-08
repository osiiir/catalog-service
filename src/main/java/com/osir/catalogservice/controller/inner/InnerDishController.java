package com.osir.catalogservice.controller.inner;

import com.osir.catalogservice.service.DishService;
import com.osir.takeoutpojo.entity.Dish;
import com.osir.takeoutpojo.vo.DishVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/catalog/inner/dish")
public class InnerDishController {

    private final DishService dishService;

    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public DishVO getByDishId(@RequestParam("id") Long id) {
        log.info("根据id查询菜品：{}", id);
        return dishService.getById(id);
    }

    /**
     * 根据条件统计菜品数量
     * @param map
     * @return
     */
    @PostMapping("/countDishByMap")
    Integer countByMap(@RequestBody Map map){
        log.info("根据条件统计菜品数量：{}", map);
        return dishService.countByMap(map);
    }

}
