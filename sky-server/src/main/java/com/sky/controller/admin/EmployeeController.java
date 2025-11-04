package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.valid.groups.Update;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
@Validated
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody @Valid EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        // 將數據封裝返回給前端
        // build 需要在封裝對象上註解@Builder
        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("退出登录")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增員工
     * @param employeeDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增員工")
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增員工： {}", employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }

    /**
     * 員工分頁查詢
     * @param employeePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("員工分頁查詢")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("員工分頁查詢，參數為:{}", employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 啟用 / 禁用 員工帳號狀態
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("啟用禁用員工帳號狀態")
    public Result startOrStop(@PathVariable Integer status, Long id){
        log.info("啟用禁用員工帳號： {}, {}", status, id);
        employeeService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * 根據id查詢員工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根據id查詢員工信息")
    public Result<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

    /**
     * 編輯員工信息
     * @param employeeDTO
     * @return
     */
    @PutMapping
    @ApiOperation("編輯員工信息")
    public Result update(@RequestBody EmployeeDTO employeeDTO){
        log.info("編輯員工信息：{}", employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }


//    @GetMapping("/page")
//    @ApiOperation("获取员工列表")
//    public Result<PageResult<Employee>> listEmployee(EmployeePageQueryDTO employeePageQueryDTO) {
//        log.info("员工分页查询，参数为：{}", employeePageQueryDTO);
//        PageResult<Employee> employeePageResult = employeeService.listEmployee(employeePageQueryDTO);
//        return Result.success(employeePageResult);
//    }

//    @PostMapping("/status/{status}")
//    @ApiOperation("修改员工状态")
//    public Result<?> changeEmployeeStatus(
//            @ApiParam(value = "员工状态", required = true)
//            @PathVariable
//            @Range(max = 1L, message = "status不合法")
//            @NotNull(message = "status不能为空")
//            Integer status,
//
//            @RequestParam
//            @NotNull(message = "员工ID不能为空")
//            @ApiParam(value = "员工ID", required = true)
//            Long id
//    ) {
//        log.info("员工修改状态，id：{}，状态值：{}", id, status);
//        employeeService.changeEmployeeStatus(status, id);
//        return Result.success();
//    }
//
//    @GetMapping("/{id}")
//    @ApiOperation("根据ID查询员工信息")
//    public Result<Employee> getEmployeeById(
//            @PathVariable
//            @ApiParam(value = "员工ID", required = true)
//            @NotNull(message = "员工ID不能为空")
//            Long id
//    ) {
//        log.info("获取员工信息，id: {}", id);
//        Employee employee = employeeService.getEmployeeById(id);
//        return Result.success(employee);
//    }
//
//    @PutMapping
//    @ApiOperation("更新员工信息")
//    public Result<?> updateEmployee(@RequestBody @Validated(Update.class) EmployeeDTO employeeDTO) {
//        log.info("编辑员工信息：{}", employeeDTO);
//        employeeService.updateEmployee(employeeDTO);
//        return Result.success();
//    }
}
