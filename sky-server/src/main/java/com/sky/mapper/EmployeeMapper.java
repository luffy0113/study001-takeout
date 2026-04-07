package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.annotation.OperationType;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper  // 告诉 MyBatis：这是一个 Mapper 接口，帮我生成实现类
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     */
    @Select("SELECT * FROM employee WHERE username = #{username}")
    Employee getByUsername(String username);

    /**
     * 添加员工
     */
    @AutoFill(OperationType.INSERT)    // ★ 新增：贴上注解
    @Insert("INSERT INTO employee (name, username, password, phone, sex, id_number, " +
            "status, create_time, update_time, create_user, update_user) " +
            "VALUES " +
            "(#{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, " +
            "#{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insert(Employee employee);

    /**
     * 分页查询
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用禁用
     */
    void update(Employee employee);

    /**
     * 根据id查询员工
     */
    @AutoFill(OperationType.UPDATE)
    @Select("SELECT * FROM employee WHERE id = #{id}")
    Employee getById(Long id);
}