package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.annotation.AutoSet;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

//    @Insert("insert into employee (name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user) " +
//            "values (#{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
//    @AutoSet(OperationType.INSERT)
//    int insertEmployee(Employee employee);

//    Page<Employee> listEmployee(EmployeePageQueryDTO employeePageQueryDTO);

//    @Select("select * from employee where id = #{id}")
//    Employee getById(Long id);

//    @AutoSet(OperationType.UPDATE)
//    void update(Employee employee);

    /**
     *  插入員工數據
     * @param employee
     */
    @Insert("insert into sky_take_out.employee(name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user) " +
            "values " +
            "(#{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    @AutoFill(OperationType.INSERT)
    void insert(Employee employee);

    /**
     * 員工分頁查詢
     * @param employeePageQueryDTO
     * @return
     *
     * 動態SQL就不用使用註解方式，因為會使用到SQL動態標籤
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 根據主鍵動態修改屬性
     *
     * 啟用 / 禁用 員工帳戶狀態
     * 編輯員工信息
     * @param employee
     */
    @AutoFill(OperationType.UPDATE)
    void update(Employee employee);

    /**
     * 根據id查詢員工信息
     * @param id
     * @return
     */
    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);
}
