package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
@ApiModel(description = "分类分页查询参数")
public class CategoryPageQueryDTO implements Serializable {

    @ApiModelProperty("页码")
    private int page;

    @ApiModelProperty("每页记录数")
    private int pageSize;

    @ApiModelProperty("分类名称")
    private String name;           // 可选，按名称搜索

    @ApiModelProperty("分类类型：1菜品分类 2套餐分类")
    private Integer type;          // 可选，按类型筛选
}