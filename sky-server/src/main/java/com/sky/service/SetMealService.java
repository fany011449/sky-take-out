package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetMealService {
//    boolean saveSetMeal(SetmealDTO setmealDTO);

//    PageResult<SetmealVO> listAllSetMeal(SetmealPageQueryDTO setmealPageQueryDTO);

//    SetmealVO getSetMealById(Long id);

//    boolean updateSetMeal(SetmealDTO setmealDTO);
//
//    boolean updateSetMealStatus(Long id, Integer status);
//
//    boolean deleteSetMealByIds(List<Long> ids);
//
    List<Setmeal> getSetMealListByCategoryId(Long categoryId);
//
    List<DishItemVO> getDishListBySetMealId(Long id);


    /**
     * 新增套餐
     * @param setmealDTO
     */
    void insertSetMeal(SetmealDTO setmealDTO);

    /**
     * 套餐分頁查詢
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 根據ID查詢套餐
     *
     * @param id
     * @return
     */
    SetmealVO queryById(Long id);

    /**
     * 修改套餐
     * @param setmealDTO
     */
    void updateSetmeal(SetmealDTO setmealDTO);

    /**
     * 刪除套餐
     * @param ids
     */
    void deleteBatch(List<Long> ids);
}
