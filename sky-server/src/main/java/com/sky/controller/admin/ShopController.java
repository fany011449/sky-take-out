package com.sky.controller.admin;

import com.sky.constant.RedisConstant;
import com.sky.constant.ShopStatusConstant;
import com.sky.result.Result;
import com.sky.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController("adminShopController") // 區分開來user / admin 相同類名
@RequestMapping("/admin/shop")
@Api(tags = "店鋪相關接口")
@Slf4j
public class ShopController {


    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 設置店鋪營業狀態
     *
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    @ApiOperation("設置店鋪營業狀態")
    public Result setStatus(@PathVariable Integer status) {
        log.info("設置店鋪營業狀態為：{}", status.equals(ShopStatusConstant.ENABLE) ? "營業中" : "打烊中");
        redisTemplate.opsForValue().set(RedisConstant.SHOP_STATUS_KEY, status);
        return Result.success();
    }

    /**
     * 獲取店鋪的營業狀態
     *
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("獲取店鋪的營業狀態")
    public Result<Integer> getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(RedisConstant.SHOP_STATUS_KEY);
        log.info("獲取店鋪的營業狀態為：{}", status.equals(ShopStatusConstant.ENABLE) ? "營業中" : "打烊中");
        return Result.success(status);

    }
}
