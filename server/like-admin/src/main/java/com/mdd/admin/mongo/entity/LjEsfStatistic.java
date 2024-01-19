package com.mdd.admin.mongo.entity;

import java.io.Serializable;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 链家房源数量统计
 */
@Data
@Document(collection = "esf_statistic")
public class LjEsfStatistic implements Serializable{
	
	/** serialVersionUID*/  
	private static final long serialVersionUID = 1336221069864169430L;
	
	@Id
	private String id;
	/**
	 * 城市编码
	 */
	@Field(value = "city_key")
	private String cityKey;
	/**
	 * 城市
	 */
    private String city;
    /**
     * 区
     */
    private String district;	
    /**
     * 街道|商圈
     */
    private String block;
    /**
     * 链接
     */
    private String url;
    /**
     * 房源数量
     */
    private Integer numbers;
    /**
     * md5Url
     */
    private String md5Url;
    /**
     * 创建时间
     */
    private String createDay;
    /**
     * 上期数量
     */
    private Integer prenums;
    /**
     * 数量涨跌
     */
    private Integer diff;
    /**
     * 统计类型： 1-城市，2-区域，3-街道|商圈
     */
    private Integer type;
    /**
     * 省份
     */
    private String province;
}
