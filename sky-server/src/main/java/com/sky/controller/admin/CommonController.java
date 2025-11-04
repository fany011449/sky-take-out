package com.sky.controller.admin;

import com.sky.annotation.validation.File;
import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import com.sky.utils.CloudFlareR2Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 通用接口
 */
@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {

    @Autowired
    private CloudFlareR2Util cloudFlareR2Util;
    /**
     * 文件上傳
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("文件上傳")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上傳 {} . . .", file.getOriginalFilename());

        try {
            String url = cloudFlareR2Util.upload(file);

            return Result.success(url);
        } catch (Exception e) {
            log.error("文件上傳失敗 ： {} ", e);
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
