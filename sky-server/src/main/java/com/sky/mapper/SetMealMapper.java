package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.annotation.AutoSet;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealOverViewVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SetMealMapper {

//    // 根据分类ID查找套餐
    @Select("select count(1) from setmeal where category_id = #{id}")
    int getCountByCategoryId(Long id);
//
//    @Insert("insert into setmeal (category_id, name, price, status, description, image, create_time, update_time, create_user, update_user) " +
//            "values (#{categoryId}, #{name}, #{price}, #{status}, #{description}, #{image}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
//    @Options(useGeneratedKeys = true, keyProperty = "id")
//    @AutoSet(OperationType.INSERT)
//    int saveSetMeal(Setmeal setmeal);
//
//    @Select("select count(1) from setmeal where name = #{namd}")
//    int getByMealName(String name);
//
//    Page<SetmealVO> listAllSetMeal(SetmealPageQueryDTO setmealPageQueryDTO);
//
//
//
//    @AutoSet(OperationType.UPDATE)
//    int updateSetMeal(Setmeal setmeal);
//
    List<Long> getSellingSetMealByIds(List<Long> ids);
//
//    int deleteSetMealByIds(List<Long> ids);
//
//    List<String> getSetMealImagesByIds(List<Long> ids);
//
//    @Select("select * from setmeal where status = 1 and category_id = #{categoryId}")
//    List<Setmeal> getSetMealListByCategoryId(Long categoryId);
//
    SetmealOverViewVO getAllStatusSetMealCount();




















    /**
     * 新增套餐
     * @param setmeal
     */
    @AutoFill(OperationType.INSERT)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Setmeal setmeal);

    /**
     * 套餐分頁查詢
     * @param setmealPageQueryDTO
     * @return
     */
    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);


    /**
     * 根據ID查詢套餐
     *
     * @param id
     * @return
     */
    SetmealVO getSetMealById(Long id);

    /**
     * 根據主鍵ID查詢套餐
     * @param id
     * @return
     */
    @Select("select * from sky_take_out.setmeal where id = #{id}")
    Setmeal getById(Long id);

    /**
     * 根據主鍵ID刪除套餐
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 根據ID更新套餐基本信息
     * @param setmeal
     */
    @AutoFill(OperationType.UPDATE)
    void updateById(Setmeal setmeal);
}
