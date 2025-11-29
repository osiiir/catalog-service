package com.osir.catalogservice.controller.admin;


import com.osir.catalogservice.service.CategoryService;
import com.osir.takeoutpojo.dto.CategoryDTO;
import com.osir.takeoutpojo.dto.CategoryPageQueryDTO;
import com.osir.takeoutpojo.entity.Category;
import com.osir.takeoutpojo.result.PageResult;
import com.osir.takeoutpojo.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @CacheEvict(cacheNames = "categoryCache",allEntries = true)
    public Result save(@RequestBody CategoryDTO categoryDTO){
        log.info("新增分类：{}", categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result page(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("分页查询：{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    @CacheEvict(cacheNames = "categoryCache",allEntries = true)
    public Result deleteById(@RequestParam("id") Long id){
        log.info("删除分类：{}", id);
        categoryService.deleteById(id);
        return Result.success();
    }

    /**
     * 修改分类
     * @param categoryDTO
     * @return
     */
    @PutMapping
    @CacheEvict(cacheNames = "categoryCache" , key = "#categoryDTO.id")
    public Result update(@RequestBody CategoryDTO categoryDTO){
        log.info("修改分类：{}",categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }

    /**
     * 启用、禁用分类
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @CacheEvict(cacheNames = "categoryCache",allEntries = true)
    public Result startOrStop(@PathVariable("status") Integer status, Long id){
        log.info("启用、禁用分类：{}", id);
        categoryService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    @GetMapping("/list")
    public Result list(@RequestParam("type") Integer type){
        log.info("根据类型查询分类：{}", type);
        Category category = new Category();
        if(type!=null) category.setType(type);

        List<Category> list = categoryService.list(category);
        return Result.success(list);
    }
}
