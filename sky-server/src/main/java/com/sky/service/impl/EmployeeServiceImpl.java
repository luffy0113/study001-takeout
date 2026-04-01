package com.sky.service.impl;

import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.mapper.EmployeeMapper;
import com.sky.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    //员工登录
    @Override
    public Employee login(EmployeeLoginDTO employeeLoginDTO){
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //根据用户名匹配用户
        Employee employee = employeeMapper.getByUsername(username);

        //账号不存在
        if (employee == null){
            throw new RuntimeException("账号不存在");
        }

        //密码错误（但密码要先加密）
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())){
            throw new RuntimeException("密码错误");
        }

        //账户被锁定
        if (employee.getStatus() == 0){
            throw new RuntimeException("账户被锁定");
        }
        return employee;
    }

}
