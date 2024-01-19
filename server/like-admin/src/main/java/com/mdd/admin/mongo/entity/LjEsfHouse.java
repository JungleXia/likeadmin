package com.mdd.admin.mongo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 链家二手房
 */
@Data
@Document(collection = "lj_esf_house")
public class LjEsfHouse implements Serializable{

	/** serialVersionUID*/  
	private static final long serialVersionUID = -1528641677974815742L;
	
	@Id
	private String id;
	/**
	 * 房源链接
	 */
	private String url;
	/**
	 * md5Url
	 */
	private String md5Url;
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
	 *  标题
	 */
	private String title;
	/**
	 * 房源编号
	 */
	private String houseNo;
	/**
	 *  总价
	 */
	private BigDecimal totalPrice;
	/**
	 *  单价
	 */
	private BigDecimal unitPrice;
	/**
	 * 小区名
	 */
	private String community;
	/**
	 * 小区链接
	 */
	private String communityUrl;
	/**
	 *  房屋户型，几室几厅
	 */
	private String houseType;
	/**
	 *  完整户型
	 */
	private String huxing;
	/**
	 *  几室
	 */
	private int room;
	/**
	 *  几厅
	 */
	private int hall;
	/**
	 *  建筑面积
	 */
	private BigDecimal area;
	/**
	 *  所在楼层
	 */
	private String floor;
	/**
	 *  建筑年份，如：2017年
	 */
	private String buildYear;
	/**
	 *  朝向
	 */
	private String orientation;
	/**
	 *  装修情况，精装、毛坯
	 */
	private String decorationType;
	/**
	 *  封面图
	 */
	private String picUrl;
	/**
	 *  用途，交易权属：住宅、公寓、别墅、其他
	 */
	private String useType;
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
	 * 过期状态
	 */
	private boolean expired;
	/**
	 * 涨跌额
	 */
	private float diff;
	/**
	 * 涨跌比例
	 */
	private float diffRate;
	/**
	 * 关注人数
	 */
	private Integer followers;
	/**
	 * 发布时间
	 */
	private String publish;
	/**
	 * 来源页面
	 */
	private String pageUrl;
	/**
	 * 状态 -1：跌价，1：涨价，2：上新
	 */
	private Integer status;
	/**
	 * 标签
	 */
	private List<String> tags;
}
