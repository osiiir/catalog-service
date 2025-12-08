package com.osir.catalogservice.controller.inner;

import com.osir.catalogservice.service.SetmealService;
import com.osir.takeoutpojo.entity.Setmeal;
import com.osir.takeoutpojo.vo.SetmealVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/catalog/inner/setmeal")
public class InnerSetmealController {

    private final SetmealService setmealService;

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public SetmealVO getBySetmealId(@RequestParam("id") Long id) {
        log.info("根据id查询套餐：{}", id);
        return setmealService.getById(id);
    }

    /**
     * 根据条件统计套餐数量
     * @param map
     * @return
     */
    @PostMapping("/countSetmealByMap")
    Integer countByMap(@RequestBody Map map){
        log.info("根据条件统计套餐数量：{}", map);
        return setmealService.countByMap(map);
    }

}
