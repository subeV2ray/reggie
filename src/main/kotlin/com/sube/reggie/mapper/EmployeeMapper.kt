package com.sube.reggie.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.sube.reggie.entity.Employee
import org.apache.ibatis.annotations.Mapper

/**
 * @author  Sube
 * @Date : 2023/2/23 19:37
 * @Version : 1.0
 * @Description : 员工数据访问层
 */
@Mapper
interface EmployeeMapper : BaseMapper<Employee>  {
}