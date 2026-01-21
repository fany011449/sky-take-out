package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Api(tags = "收貨地址相關接口")
@RequestMapping("/user/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 查詢當前登陸用戶的所有地址信息
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("查詢當前登陸用戶的所有地址信息")
    public Result<List<AddressBook>> list(){
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        List<AddressBook> list = addressBookService.list(addressBook);
        return Result.success(list);
    }

    /**
     * 新增地址
     * @param addressBook
     * @return
     */
    @PostMapping
    @ApiOperation("新增地址")
    public Result save(@RequestBody AddressBook addressBook){
        addressBookService.save(addressBook);
        return Result.success();
    }

    /**
     * 根據ID查詢地址
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根據ID查詢地址")
    public Result<AddressBook> getById(@PathVariable Long id){
        AddressBook addressBook = addressBookService.getById(id);
        return Result.success(addressBook);
    }

    /**
     * 根據id修改地址
     * @param addressBook
     * @return
     */
    @PutMapping
    @ApiOperation("根據id修改地址")
    public Result update(@RequestBody AddressBook addressBook){
        addressBookService.update(addressBook);
        return Result.success();
    }

    /**
     * 設置默認地址
     * @param addressBook
     * @return
     */
    @PutMapping("/default")
    @ApiOperation("設置默認地址")
    public Result setDefault(@RequestBody AddressBook addressBook){
        addressBookService.setDefault(addressBook);
        return Result.success();
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("根據ID刪除地址")
    public Result deleteById(Long id){
        addressBookService.deleteById(id);
        return Result.success();
    }

    /**
     * 查詢默認地址
     * @return
     */
    @GetMapping("/default")
    @ApiOperation("查詢默認地址")
    public Result<AddressBook> getDefault(){
        AddressBook addressBook = new AddressBook();
        addressBook.setIsDefault(1);
        addressBook.setUserId(BaseContext.getCurrentId());
        List<AddressBook> list = addressBookService.list(addressBook);

        if(!CollectionUtils.isEmpty(list)){
            return Result.success(list.get(0));
        }

        return Result.error("查詢失敗");
    }
}
