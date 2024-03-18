package com.mdd.admin.mongo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 链家二手房历史记录
 */
@Data
@Document(collection = "lj_esf_house_his")
public class LjEsfHouseHistory implements Serializable{

	/** serialVersionUID*/  
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
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
	 *  建筑面积
	 */
	private BigDecimal area;
	/**
	 * 创建时间
	 */
	private String createTime;

	private String updateTime;

	public String getCreateTime() {
		if (this.updateTime != null && this.updateTime != "") {
			return this.updateTime ;
		}
		return this.createTime;
	}
}
