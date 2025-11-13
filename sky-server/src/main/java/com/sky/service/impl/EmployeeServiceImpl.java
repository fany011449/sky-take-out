package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.ShopStatusConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        // MD5加密
        String password = DigestUtils.md5DigestAsHex(employeeLoginDTO.getPassword().getBytes());

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (Objects.equals(employee.getStatus(), ShopStatusConstant.DISABLE)) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 新增員工
     *
     * @param employeeDTO
     */
    public void save(EmployeeDTO employeeDTO) {
        // 前端資料封裝對象DTO，要轉成實體類Employee
        Employee employee = new Employee();

        // employeeDTO  --資料拷貝到--> employee
        BeanUtils.copyProperties(employeeDTO, employee);

        // 設置帳號的狀態，默認正常狀態 1:正常 0:鎖定
        employee.setStatus(ShopStatusConstant.ENABLE);

        // 設置密碼，默認密碼123456(md5加密)
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        // 使用@AutoFill，故不需要賦值
//        // 設置當前紀錄的創建時間 & 修改時間
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//
//        // 設置當前紀錄創建人id與修改人id
//        employee.setCreateUser(BaseContext.getCurrentId());
//        employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.insert(employee);
    }

//    @Override
//    public boolean save(EmployeeDTO employeeDTO) {
//
//        // 寻找当前的要添加的用户名是否已被占用
//        Employee em = employeeMapper.getByUsername(employeeDTO.getUsername());
//        if (em != null) {
//            throw new BusinessException(employeeDTO.getUsername() + "用户名已被注册");
//        }
//
//        Employee employee = new Employee();
//        BeanUtils.copyProperties(employeeDTO, employee);
//
////        LocalDateTime now = LocalDateTime.now();
////        employee.setCreateTime(now);
////        employee.setUpdateTime(now);
//
//        // 设置默认密码
//        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
//        // 数据库status默认状态为1
//
//        // threadLocal获取当前登录用户
////        Long empId = BaseContext.getCurrentId();
////        employee.setCreateUser(empId);
////        employee.setUpdateUser(empId);
//        return employeeMapper.insertEmployee(employee) > 0;
//    }


//    @Override
//    public PageResult<Employee> listEmployee(EmployeePageQueryDTO employeePageQueryDTO) {
//        Page<Employee> employeePage = PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize())
//                .doSelectPage(() -> employeeMapper.listEmployee(employeePageQueryDTO));
//        return new PageResult<>(employeePage.getTotal(), employeePage.getResult(), employeePage.getPageSize(), employeePage.getPageNum());
//    }

//    @Override
//    public void changeEmployeeStatus(Integer status, Long id) {
//        Employee employee = getEmployeeById(id);
//
//        employee.setStatus(status);
//        employee.setUpdateUser(BaseContext.getCurrentId());
//        employee.setUpdateTime(LocalDateTime.now());
//
//        employeeMapper.update(employee);
//    }

//    @Override
//    public Employee getEmployeeById(Long id) {
//        Employee employee = employeeMapper.getById(id);
//        if (employee == null) {
//            throw new BusinessException("员工id不存在");
//        }
//        return employee;
//    }

//    @Override
//    public void updateEmployee(EmployeeDTO employeeDTO) {
//        Employee employee = getEmployeeById(employeeDTO.getId());
//
//        BeanUtils.copyProperties(employeeDTO, employee);
//        employee.setUpdateUser(BaseContext.getCurrentId());
//        employee.setUpdateTime(LocalDateTime.now());
//
//        employeeMapper.update(employee);
//    }

    /**
     * 員工分頁查詢
     *
     * @param employeePageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        // select * from employee Limit 0,10
        // 使用pageHelper
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());

        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);

        long total = page.getTotal();
        List<Employee> records = page.getResult();

        return new PageResult(total, records);
    }

    /**
     * 啟用 / 禁用 員工帳號狀態
     *
     * @param status
     * @param id
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        //update employee set status = ? where id = ?

        // 方法一：傳統方式
//        Employee employee = new Employee();
//        employee.setStatus(status);
//        employee.setId(id);

        // 方法二：透過Employee.class上的@builder
        Employee employee = Employee.builder()
                .status(status)
                .id(id)
                .build();

        employeeMapper.update(employee);
    }

    /**
     * 根據id查詢員工信息
     * @param id
     * @return
     */
    @Override
    public Employee getById(Long id) {
        Employee employee = employeeMapper.getById(id);
        // 屏蔽密碼
        employee.setPassword("*****");
        return employee;
    }

    /**
     * 編輯員工信息
     * @param employeeDTO
     * @return
     */
    @Override
    public void update(EmployeeDTO employeeDTO) {
        //  因為EmployeeMapper中的update是接受Employee類，所以要DTO要轉型
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee); // 屬性拷貝

        // 使用@AutoFill，故不需要賦值
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.update(employee);
    }
}
