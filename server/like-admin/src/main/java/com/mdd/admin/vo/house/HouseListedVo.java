package com.mdd.admin.vo.house;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author JungleXia
 * @version 1.0
 * @title HouseVo
 * @description 房源信息展示
 * @date 2024/1/19 13:47:55
 */
@Data
@ApiModel("二手房列表Vo")
public class HouseListedVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "源链接")
    private String url;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "区")
    private String district;

    @ApiModelProperty(value = "街道|商圈")
    private String block;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "房源编号")
    private String houseNo;

    @ApiModelProperty(value = "总价")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "小区名")
    private String community;

    @ApiModelProperty(value = "户型")
    private String houseType;

    @ApiModelProperty(value = "面积")
    private BigDecimal area;

    @ApiModelProperty(value = "楼层")
    private String floor;

    @ApiModelProperty(value = "建筑年份")
    private String buildYear;

    @ApiModelProperty(value = "朝向")
    private String orientation;

    @ApiModelProperty(value = "装修")
    private String decorationType;

    @ApiModelProperty(value = "来源")
    private String dataFrom;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    @ApiModelProperty(value = "涨跌额")
    private float diff;

    @ApiModelProperty(value = "涨跌幅度")
    private float diffRate;

    @ApiModelProperty(value = "关注人数")
    private Integer followers;

    @ApiModelProperty(value = "发布时间")
    private String publish;

    @ApiModelProperty(value = "状态", notes = "-1：跌价，1：涨价，2：上新")
    private Integer status;

}
