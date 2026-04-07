package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data                // 自动生成 getter/setter/toString（你已经知道了）
@Builder             // 允许用 Category.builder().name("xxx").build() 方式创建对象
@NoArgsConstructor   // 自动生成无参构造方法
@AllArgsConstructor  // 自动生成全参构造方法

public class Category implements Serializable {

    private Long id;                // 主键，对应数据库的 id
    private Integer type;           // 类型：1 菜品分类，2 套餐分类
    private String name;            // 分类名称，比如 "酒水饮料"、"人气套餐"
    private Integer sort;           // 排序字段，数字越小越靠前
    private Integer status;         // 状态：0 禁用，1 启用
    private LocalDateTime createTime;   // 创建时间
    private LocalDateTime updateTime;   // 修改时间
    private Long createUser;        // 创建人的id
    private Long updateUser;        // 修改人的id
}