package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
@ApiModel(description = "新增/修改分类时传递的数据")
public class CategoryDTO implements Serializable {

    @ApiModelProperty("分类id")
    private Long id;            // 新增时不传，修改时传

    @ApiModelProperty("类型：1 菜品分类  2 套餐分类")
    private Integer type;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("排序")
    private Integer sort;
}