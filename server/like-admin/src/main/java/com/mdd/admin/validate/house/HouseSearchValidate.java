package com.mdd.admin.validate.house;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author JungleXia
 * @version 1.0
 * @title HouseSearchValidate
 * @description 房源搜索参数
 * @date 2024/1/19 14:01:04
 */
@Data
@ApiModel("文章搜索参数")
public class HouseSearchValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "区")
    private String district;

    @ApiModelProperty(value = "街道|商圈")
    private String block;

    @ApiModelProperty(value = "房源编号")
    private String houseNo;

    @ApiModelProperty(value = "总价")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "小区名")
    private String community;

    @ApiModelProperty(value = "状态", notes = "-1：跌价，1：涨价，2：上新")
    private Integer status;

    @ApiModelProperty(value = "状态")
    private boolean expired;

}


