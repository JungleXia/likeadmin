package com.mdd.admin.validate.house;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author JungleXia
 * @version 1.0
 * @title CommunitySearchValidate
 * @description 小区搜索参数
 * @date 2024/3/19 11:01:04
 */
@Data
@ApiModel("小区搜索参数")
public class CommunitySearchValidate extends SearchValidate {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "区")
    private String district;

    @ApiModelProperty(value = "街道|商圈")
    private String block;

    @ApiModelProperty(value = "小区编号")
    private String cNo;

    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "小区名")
    private String community;

    @ApiModelProperty(value = "状态", notes = "-1：跌价，1：涨价，2：上新")
    private Integer status;
}


