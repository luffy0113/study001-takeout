package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
@ApiModel(description = "新增/编辑员工时传递的数据模型")
public class EmployeeDTO implements Serializable {

    @ApiModelProperty("员工id")
    private Long id;            // 新增时前端不传，编辑时前端会传

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("身份证号")
    private String idNumber;

    private String password;
}