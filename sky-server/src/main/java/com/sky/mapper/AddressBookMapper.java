package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddressBookMapper {
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
    @Insert("insert into sky_take_out.address_book" +
            "(user_id, consignee, sex, phone, province_code, province_name, city_code, city_name, district_code, district_name, detail, label, is_default) " +
            "values (#{userId}, #{consignee}, #{sex}, #{phone}, #{provinceCode}, #{provinceName}, #{cityCode}, #{cityName}, #{districtCode}, #{districtName}, #{detail}, #{label}, #{isDefault})")
    void insert(AddressBook addressBook);

    /**
     * 根據ID查詢地址
     * @param id
     * @return
     */
    @Select("select * from sky_take_out.address_book where id = #{id}")
    AddressBook getById(Long id);

    /**
     * 根據id修改地址
     * @param addressBook
     */
    void update(AddressBook addressBook);

    /**
     *  將所有地址改為非默認地址
     * @param addressBook
     */
    @Update("update sky_take_out.address_book set is_default = #{isDefault} where user_id = #{userId}")
    void updateIsDefaultByUserId(AddressBook addressBook);

    /**
     * 根據ID刪除地址
     * @param id
     */
    @Delete("delete from sky_take_out.address_book where id = #{id}")
    void deleteById(Long id);
}
