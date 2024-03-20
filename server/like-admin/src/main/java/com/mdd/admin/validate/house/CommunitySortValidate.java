package com.mdd.admin.validate.house;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author JungleXia
 * @version 1.0
 * @title CommunitySortValidate
 * @description 小区排序参数
 * @date 2024/3/19 11:02:24
 */
@Data
@ApiModel(value = "小区搜索参数")
public class CommunitySortValidate extends SortValidate{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "房源数量排序")
    private String numbersSort;

    @ApiModelProperty(value = "均价排序")
    private String unitPriceSort;

    @ApiModelProperty(value = "成交套数排序")
    private String dealNumSort;

    @ApiModelProperty(value = "数量涨跌排序")
    private String diffNumSort;

    @ApiModelProperty(value = "均价涨跌排序")
    private String diffPriceSort;
}
