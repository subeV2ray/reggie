package com.sube.reggie.service.impl

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.sube.reggie.entity.Employee
import com.sube.reggie.mapper.EmployeeMapper
import com.sube.reggie.result.Result
import com.sube.reggie.service.EmployeeService
import org.apache.commons.lang.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.util.DigestUtils
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

/**
 * @author  Sube
 * @Date 2023/2/23 19:42
 * @version  1.0
 * @description  员工业务逻辑实现
 */
@Service
class EmployeeServiceImpl : ServiceImpl<EmployeeMapper, Employee>(), EmployeeService {

    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * 员工登录
     */
    override fun login(employee: Employee, request: HttpServletRequest): Result<Employee> {
        // 查看用户名是否存在
        val username = employee.username
        val wrapper = LambdaQueryWrapper<Employee>().eq(Employee::getUsername, username)
        // 用户名不存在，返回错误
        if (getOne(wrapper) == null) {
            return Result.error("用户名或密码错误")
        }
        // 用户名存在，校验密码
        val password = DigestUtils.md5DigestAsHex(employee.password.toByteArray())
        wrapper.eq(Employee::getPassword, password)
        // 不存在，返回错误
        val one = getOne(wrapper) ?: return Result.error("用户名或密码错误")
        // 账号状态禁用，返回错误
        if (one.status == 0) {
            return Result.error("账户已禁用，请联系管理员")
        }
        // 存在，保存用户id到session中
        request.session.setAttribute("employee", one.id ?: Long)
        logger.info("\n员工id为：{}", one.id ?: Long)
        // 返回用户信息
        return Result.success(one)
    }

    /**
     * 退出登录
     */
    override fun logout(request: HttpServletRequest): Result<String> {
        request.session.removeAttribute("employee")
        return Result.success("退出成功")
    }

    /**
     * 新增员工
     */
    override fun add(employee: Employee, request: HttpServletRequest): Result<String> {

        logger.info("新增员工为{}", employee.toString())

        employee.password = DigestUtils.md5DigestAsHex("123456".toByteArray())
        employee.createTime = LocalDateTime.now()
        employee.updateTime = LocalDateTime.now()
        val empId = request.session.getAttribute("employee") as? Long
        employee.createUser = empId
        employee.updateUser = empId
        return if(save(employee))
            Result.success("添加成功")
        else
            Result.success("添加失败")
    }

    /**
     * 更改员工状态
     */
    override fun status(employee: Employee): Result<String> {
        val wrapper = LambdaQueryWrapper<Employee>().eq(Employee::getId, employee.id)
        val one = getOne(wrapper)
        // 更改状态
        one.status = employee.status
        return if(updateById(one)) {
            Result.success("操作成功")
        } else
            Result.error("操作失败")


    }

    /**
     * 编辑回显数据
     */
    override fun showById(id: Long): Result<Employee> {
        val employee = getById(id)
        return if(employee != null)
            Result.success(employee)
        else
            Result.error("状态异常")
    }

    /**
     * 编辑员工
     */
    override fun edit(employee: Employee): Result<String> {
        return if (updateById(employee))
            Result.success("编辑成功")
        else
            Result.error("编辑失败，请检查填写内容")
    }

    /**
     * 分页查询
     */
    override fun pageInfo(page: Long, pageSize: Long, name: String?): Result<Page<Employee>> {
        // 分页
        val pageInfo = Page<Employee>(page, pageSize)
        // 查询条件
        val wrapper = LambdaQueryWrapper<Employee>()
            .like(StringUtils.isNotEmpty(name), Employee::getName, name)
            .orderByDesc(Employee::getCreateTime)
        // 查询结果
        val employeePage = this.page(pageInfo, wrapper)
        // 返回
        return Result.success(employeePage)
    }
}