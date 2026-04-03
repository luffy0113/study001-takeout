package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

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

    // 新增员工
    @Override
    public void save(EmployeeDTO employeeDTO) {

        // 第一步：把 DTO 转成实体类 Employee
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);

        // 第二步：设置 DTO 里没有的字段（后端自己填的）
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));  // 默认密码 123456，MD5加密
        employee.setStatus(1);                          // 默认启用
        employee.setCreateTime(LocalDateTime.now());    // 创建时间 = 现在
        employee.setUpdateTime(LocalDateTime.now());    // 修改时间 = 现在

        // TODO：createUser 和 updateUser 先写死，后面学了 ThreadLocal 再改
        employee.setCreateUser(1L);                     // 先写死为管理员id=1
        employee.setUpdateUser(1L);

        // 第三步：调用 Mapper 插入数据库
        employeeMapper.insert(employee);
    }

    // 分页查询
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {

        // 第一步：告诉 PageHelper "我要查第几页，每页几条"
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());

        // 第二步：正常查询，PageHelper 会自动在 SQL 后面加 LIMIT
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);

        // 第三步：把结果封装成 PageResult 返回
        return new PageResult(page.getTotal(), page.getResult());
    }

    // 启用禁用
    @Override
    public void startOrStop(Integer status, Long id) {

        Employee employee = Employee.builder()
                .id(id)
                .status(status)
                .updateTime(LocalDateTime.now())
                .updateUser(1L)    // 先写死，后面改
                .build();

        employeeMapper.update(employee);
    }

    @Override
    public Employee getById(Long id) {
        Employee employee = employeeMapper.getById(id);

        // 把密码隐藏掉，不要返回给前端
        employee.setPassword("****");

        return employee;
    }

    // 编辑员工信息
    @Override
    public void update(EmployeeDTO employeeDTO) {

        // 把 DTO 转成实体类
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        // 补充修改时间和修改人
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(1L);    // 先写死，后面改

        // 调用通用的 update 方法
        employeeMapper.update(employee);
    }

}
