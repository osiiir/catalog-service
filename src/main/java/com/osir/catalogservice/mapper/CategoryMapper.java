package com.osir.catalogservice.mapper;

import com.github.pagehelper.Page;

import com.osir.catalogservice.annotation.AutoFill;
import com.osir.catalogservice.enumeration.OperationType;
import com.osir.takeoutpojo.dto.CategoryPageQueryDTO;
import com.osir.takeoutpojo.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 插入数据
     * @param category
     */
    @AutoFill(op = OperationType.INSERT,pos = 0)
    @Insert("insert into category(type, name, sort, status, create_time, update_time, create_user, update_user)" +
            " VALUES" +
            " (#{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insert(Category category);

    /**
     * 分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 根据id删除分类
     * @param id
     */
    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);

    /**
     * 根据id修改分类
     * @param category
     */
    @AutoFill(op = OperationType.UPDATE,pos = 0)
    void update(Category category);

    /**
     * 根据类型查询分类
     * @param category
     * @return
     */
    List<Category> list(Category category);
}
