package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.valid.groups.Add;
import com.sky.valid.groups.Update;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相關接口")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 新增菜品
     * @param dishDTO
     * @return
     */
    @ApiOperation("新增菜品")
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品：{}", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * 菜品分頁查詢
     * @param dishPageQueryDTO
     * @return
     */
    @ApiOperation("菜品分頁查詢")
    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分頁查詢:{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }


    /**
     * 菜品批量刪除
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("菜品批量刪除")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("菜品批量刪除： {}", ids);
        dishService.deleteBatch(ids);

        return Result.success();
    }

    /**
     * 根據ID查詢菜品
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根據ID查詢菜品")
    public Result<DishVO> getById(@PathVariable Long id){
        log.info("根據id查詢菜品: {}", id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);

        return Result.success(dishVO);
    }

    /**
     * 修改菜品
     * @param dishDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改菜品")
    public Result update(@RequestBody DishDTO dishDTO){
        log.info("修改菜品:{}", dishDTO);
        dishService.updateWithFlavor(dishDTO);
        return Result.success();
    }
//    @Autowired
//    private DishService dishService;
//
//    @ApiOperation("新增菜品")
//    @PostMapping
//    public Result<?> saveDish(@RequestBody @Validated(Add.class) DishDTO dishDTO) {
//        boolean result = dishService.saveDish(dishDTO);
//        return result ? Result.success() : Result.error("添加菜品失败");
//    }
//
//    @ApiOperation("分页查询菜品列表")
//    @GetMapping("/page")
//    public Result<PageResult<DishVO>> getDishList(@Valid DishPageQueryDTO dishPageQueryDTO) {
//        PageResult<DishVO> dishVOPageResult = dishService.getDishList(dishPageQueryDTO);
//        return Result.success(dishVOPageResult);
//    }
//
//    @ApiOperation("删除菜品")
//    @DeleteMapping
//    public Result<?> deleteDish(
//            @RequestParam
//            @ApiParam("需要删除的菜品ID")
//            @NotEmpty(message = "菜品ID不能为空")
//            List<Long> ids
//    ) {
//        log.info("删除菜品：{}", ids);
//        boolean result = dishService.deleteDishByIds(ids);
//        return result ? Result.success() : Result.error("删除失败");
//    }
//
//    @ApiOperation("根据ID查询菜品")
//    @GetMapping("{id}")
//    public Result<DishVO> getDishVOById(@PathVariable Long id) {
//        log.info("查询菜品：{}", id);
//        DishVO dishVO = dishService.getDishVOById(id);
//        return Result.success(dishVO);
//    }
//
//    @ApiOperation("修改菜品")
//    @PutMapping
//    public Result<?> updateDish(@RequestBody @Validated(Update.class) DishDTO dishDTO) {
//        log.info("更新菜品：{}", dishDTO);
//        boolean result = dishService.updateDish(dishDTO);
//        return result ? Result.success() : Result.error("更新失败");
//    }
//
//    @ApiOperation("菜品起售、停售")
//    @PostMapping("/status/{status}")
//    public Result<?> updateDishStatus(
//            @PathVariable
//            @Range(max = 1L, message = "菜品状态不合法")
//            @ApiParam(value = "菜品状态", allowableValues = "0, 1", required = true)
//            Integer status,
//
//            @ApiParam(value = "菜品ID", required = true)
//            @RequestParam
//            Long id
//    ) {
//        log.info("修改菜品状态：id: {}, status: {}", id, status);
//        boolean result = dishService.updateDishStatus(id, status);
//        return result ? Result.success() : Result.error("更新失败");
//    }
//
//    @ApiOperation("根据分类ID查询菜品")
//    @GetMapping("/list")
//    public Result<List<Dish>> listDishByCategoryId(@RequestParam Long categoryId) {
//        log.info("根据分类ID查询菜品：{}", categoryId);
//        List<Dish> dishes = dishService.getDishListByCategoryId(categoryId);
//        return Result.success(dishes);
//    }
}
