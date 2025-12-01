package com.sky.mapper;

import com.sky.entity.SetmealDish;
import com.sky.vo.DishItemVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface SetMealDishMapper {

    /**
     * 根據 菜品ID 查詢 套餐ID
     *
     * @param ids
     * @return
     */
    // select setmeal_id from setmeal_dish where dish_id in (1,2,3)
    // ids數組，此Mysql為動態語句
    List<Long> getSetMealIdsByDishIds(List<Long> ids);

    List<Long> getCountByDishIds(List<Long> ids);

    void saveSetmealDishBatch(List<SetmealDish> setmealDishes);

    @Delete("delete from setmeal_dish where setmeal_id = #{id}")
    void delSetMealDishById(Long id);

    void delSetMealDishByIds(List<Long> ids);

    List<DishItemVO> getDishListBySetMealId(Long id);


    /**
     * 新增套餐
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);

    /**
     * 根據 套餐ID 批量刪除對應的 套餐菜品表
     * @param setmealIds
     */
    void deleteBySetmealIds(List<Long> setmealIds);
}
