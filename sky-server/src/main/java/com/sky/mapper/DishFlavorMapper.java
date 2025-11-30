package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    int saveBatch(List<DishFlavor> dishFlavors);

//    int deleteByDishIds(List<Long> ids);

    /**
     * 批量插入口味數據
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * 根據 菜品ID 刪除對應的 口味表dish_flavor
     * @param id
     */
    @Delete("delete from sky_take_out.dish_flavor where dish_id = #{id}")
    void deleteByDishId(Long id);

    /**
     * 根據菜品 id 查詢對應的口味數據
     * @param dishId
     * @return
     */
    @Select("select * from sky_take_out.dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getByDishId(Long dishId);
}
