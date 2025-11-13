package com.sky.controller.user;

import com.sky.constant.RedisConstant;
import com.sky.constant.ShopStatusConstant;
import com.sky.result.Result;
import com.sky.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userShopController")
@Slf4j
@Api(tags = "店铺相关接口")
@RequestMapping("/user/shop")
public class ShopController {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 獲取店鋪的營業狀態
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
