package com.sky.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.RedisConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.BusinessException;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.*;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.utils.AliOssUtil;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetMealDishMapper setMealDishMapper;

//
//    @Autowired
//    private CategoryMapper categoryMapper;
//

//
//    @Autowired
//
//    @Autowired
//    private AliOssUtil aliOssUtil;
//
//    @Autowired
//    private StringRedisTemplate redisTemplate;

    /**
     * 新增菜品和對應的口味
     *
     * @param dishDTO
     */
    @Transactional
    @Override
    public void saveWithFlavor(DishDTO dishDTO) {

        Dish dish = new Dish();
        // 屬性拷貝賦值
        BeanUtils.copyProperties(dishDTO, dish);

        // 向 菜品表 插入1條數據
        dishMapper.insert(dish);

        // 獲取insert生成的主鍵值
        Long dishId = dish.getId();

        // 獲取菜品中的口味
        List<DishFlavor> flavors = dishDTO.getFlavors();

        // flavors != null &&　flavors.size() > 0
        if (!CollectionUtils.isEmpty(flavors)) {
            // 批量插入dish_Id的值
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(dishId));

            // 向 口味表 插入N條數據
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /**
     * 菜品分頁查詢
     *
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 菜品批量刪除
     *
     * @param ids
     * @return
     */
    @Transactional
    @Override
    public void deleteBatch(List<Long> ids) {
        // 判斷當前菜品是否能刪除 -- 使否出售中
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                // 目前 菜品 狀態為出售中，無法刪除
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        // 斷當前菜品是否能刪除 --  菜品是否被套餐關聯
        List<Long> setMealIds = setMealDishMapper.getSetMealIdsByDishIds(ids);

        if (!CollectionUtils.isEmpty(setMealIds)) {
            // 當前目前 菜品 與 套餐 關聯，無法刪除
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        // 刪除菜品表中的菜品數據
        for (Long id : ids) {
            dishMapper.deleteById(id);
            // 刪除菜品關聯的口味數據
            dishFlavorMapper.deletByDishId(id);
        }

    }

    /**
     * 根據ID查詢菜品
     * @param id
     * @return
     */
    @Override
    public DishVO getByIdWithFlavor(Long id) {
        // 根據id查詢菜品數據
        Dish dish = dishMapper.getById(id);

        // 根據菜品id查詢口味數據
        List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(id);

        // 將查詢到的數據封裝到DishVO
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO); // dish --- 拷貝 ---> dishVo
        dishVO.setFlavors(dishFlavors);

        return dishVO;
    }

    /**
     * 根據id修改菜品基本信息與對應口味基本信息
     * @param dishDTO
     */
    @Override
    public void updateWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        // 修改 菜品表 的基本信息
        dishMapper.update(dish);

        // 刪除 原有的 口味數據
        dishFlavorMapper.deletByDishId(dishDTO.getId());

        // 重新插入口味數據
        List<DishFlavor> flavors = dishDTO.getFlavors();
        dishFlavorMapper.insertBatch(flavors);
    }
}
