package com.osir.catalogservice.controller.user;

import com.osir.catalogservice.constant.StatusConstant;
import com.osir.catalogservice.service.CategoryService;
import com.osir.takeoutpojo.entity.Category;
import com.osir.takeoutpojo.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController("userCategoryController")
@RequestMapping("/user/category")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 查询分类
     * @param type
     * @return
     */
    @GetMapping("/list")
    @Cacheable(cacheNames = "categoryCache")
    public Result list(@RequestParam("type") Integer type) {
        Category category = new Category();
        category.setStatus(StatusConstant.ENABLE);
        if(type!=null) category.setType(type);

        List<Category> list = categoryService.list(category);
        return Result.success(list);
    }

}
