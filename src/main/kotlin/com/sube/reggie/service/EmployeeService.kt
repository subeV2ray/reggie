package com.sube.reggie.service

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService
import com.sube.reggie.entity.Employee
import com.sube.reggie.result.Result
import javax.servlet.http.HttpServletRequest

/**
 * @Author : Sube
 * @Date : 2023/2/23 19:41
 * @Version : 1.0
 * @Description : 员工业务接口
 */
interface EmployeeService : IService<Employee> {

    fun login(employee: Employee, request: HttpServletRequest): Result<Employee>

    fun logout(request: HttpServletRequest): Result<String>

    fun add(employee: Employee, request: HttpServletRequest): Result<String>

    fun status(employee: Employee): Result<String>

    fun showById(id: Long): Result<Employee>

    fun edit(employee: Employee): Result<String>

    fun pageInfo(page: Long, pageSize: Long, name: String?): Result<Page<Employee>>
}