package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {
    /**
     * 查詢當前登陸用戶的所有地址信息
     * @param addressBook
     * @return
     */
    List<AddressBook> list(AddressBook addressBook);

    /**
     * 新增地址
     * @param addressBook
     */
    void save(AddressBook addressBook);

    /**
     * 根據ID查詢地址
     * @param id
     * @return
     */
    AddressBook getById(Long id);

    /**
     * 根據id修改地址
     * @param addressBook
     */
    void update(AddressBook addressBook);

    /**
     * 設置默認地址
     * @param addressBook
     */
    void setDefault(AddressBook addressBook);

    /**
     * 根據ID刪除地址
     * @param id
     */
    void deleteById(Long id);
}
