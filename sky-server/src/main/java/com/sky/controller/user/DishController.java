package com.sky.controller.user;

import com.sky.constant.ShopStatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "菜品相关接口")
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根據分類ID查詢菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根據分類id查詢菜品")
    public Result<List<DishVO>> list(Long categoryId) {
        // 構造 redis 中的key。 規則：dish_categoryId
        String key = "dish_" + categoryId;

        // 查詢 redis 中是否存在菜品數據
        List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);
        if(!CollectionUtils.isEmpty(list)){
            // 如果存在，直接返回，無須訪問數據庫
            return Result.success(list);
        }

        // 如果不存在，查詢數據庫，將得到的數據放入redis 中
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(ShopStatusConstant.ENABLE);

        list = dishService.listWithFlavor(dish);
        redisTemplate.opsForValue().set(key, list);


        return Result.success(list);
    }
}
