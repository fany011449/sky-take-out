package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

//    boolean save(EmployeeDTO employeeDTO);

    /**
     * 新增員工
     * @param employeeDTO
     */
    void save (EmployeeDTO employeeDTO);

//    PageResult<Employee> listEmployee(EmployeePageQueryDTO employeePageQueryDTO);

//    void changeEmployeeStatus(Integer status, Long id);


//    Employee getEmployeeById(Long id);

//    void updateEmployee(EmployeeDTO employeeDTO);

    /**
     * 員工分頁查詢
     * @param employeePageQueryDTO
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     *  啟用 / 禁用 員工帳號狀態
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 根據id查詢員工信息
     * @param id
     */
    Employee getById(Long id);

    /**
     * 編輯員工信息
     * @param employeeDTO
     */
    void update(EmployeeDTO employeeDTO);
}
