package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {

    private Long id;
    private String name;
    private String username;
    private String password;
    private String phone;
    private String sex;
    private String idNumber;       // 对应数据库的 id_number
    private Integer status;
    private LocalDateTime createTime;  // 对应数据库的 create_time
    private LocalDateTime updateTime;  // 对应数据库的 update_time
    private Long createUser;       // 对应数据库的 create_user
    private Long updateUser;       // 对应数据库的 update_user
}