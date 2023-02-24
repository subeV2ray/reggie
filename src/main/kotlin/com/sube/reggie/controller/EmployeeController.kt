package com.sube.reggie.controller

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.sube.reggie.entity.Employee
import com.sube.reggie.result.Result
import com.sube.reggie.service.EmployeeService
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.util.DigestUtils
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

/**
 * @author  Sube
 * @date  2023/2/23 19:47
 * @version  1.0
 * @description  员工视图控制层
 */
@Slf4j
@RestController
@RequestMapping("/employee")
class EmployeeController {

    @Resource
    private val employeeService: EmployeeService? = null

    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * 登录
     */
    @PostMapping("/login")
    fun login(@RequestBody employee: Employee, request: HttpServletRequest): Result<Employee>? {
        logger.info("\n登录员工的信息为：{}", employee)

        return employeeService?.login(employee, request)
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    fun logout(request: HttpServletRequest): Result<String>? {
        return employeeService?.logout(request)
    }

    /**
     * 新增员工
     */
    @PostMapping
    fun save(@RequestBody employee: Employee, request: HttpServletRequest): Result<String>? {
        return employeeService?.add(employee, request)
    }

    /**
     * 禁用员工
     */
    @PutMapping("/status")
    fun status(@RequestBody employee: Employee): Result<String>? {
        return employeeService?.status(employee)
    }

    /**
     * 编辑回显数据
     */
    @GetMapping("/{id}")
    fun showById(@PathVariable("id") id: Long): Result<Employee>? {
        logger.info("员工id为：{}", id)
        return employeeService?.showById(id)
    }

    /**
     * 编辑员工
     */
    @PutMapping
    fun edit(@RequestBody employee: Employee): Result<String>? {
        return employeeService?.edit(employee)
    }
    /**
     * 分页查询
     */
    @GetMapping("/page")
    fun page(page: Long, pageSize: Long, name: String?): Result<Page<Employee>>? {
        return employeeService?.pageInfo(page, pageSize, name)
    }
}