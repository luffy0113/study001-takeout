package com.sky.mapper;

import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper  // 告诉 MyBatis：这是一个 Mapper 接口，帮我生成实现类
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     */
    @Select("SELECT * FROM employee WHERE username = #{username}")
    Employee getByUsername(String username);
}