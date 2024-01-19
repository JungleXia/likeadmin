package com.mdd.admin.mongo.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Document(collection = "lj_esf_community")
public class LjEsfCommunity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7008633486089835446L;

    @Id
    private String id;
    /**
     * 小区链接
     */
    private String url;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String district;
    /**
     * 商圈|街道
     */
    private String block;
    /**
     * 编号
     */
    private String cNo;
    /**
     * 在售房源数量
     */
    private Integer numbers;
    /**
     * 小区均价
     */
    private BigDecimal unitPrice;
    /**
     * 小区名
     */
    private String community;
    /**
     * 成交周期
     */
    private Integer dealDay;
    /**
     * dealDay内成交数量
     */
    private Integer dealNum;
    /**
     * 在租房源数量
     */
    private Integer rentNum;
    /**
     * 建筑结构
     */
    private String buildType;
    /**
     * 建筑年代
     */
    private String buildYear;

    /**
     * 标签
     */
    private List<String> tags;

    /**
     * 来源网站
     */
    private String dataFrom;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 房源数量变化
     */
    private Integer diffNum;
    /**
     * 均价涨跌
     */
    private Integer diffPrice;

    /**
     * 来源页面
     */
    private String pageUrl;
    /**
     * 状态 -1：跌价，1：涨价，2：上新
     */
    private Integer status;
}
