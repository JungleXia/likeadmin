package com.mdd.admin.vo.house;

import lombok.Data;

import java.util.List;

@Data
public class CityVo implements Comparable<CityVo>{
	
	/**
	 * 首字母
	 */
	private String initials;
	
	/**
	 * 名称
	 */
	private String name;

	/**
	 * 省
	 */
	private String province;

	/**
	 * 数量
	 */
	private Integer numbers;
	
	/**
	 * 子级
	 */
	private List<CityVo> subList;
	
	/**
	 * 是否初始展开
	 */
	private boolean expaned;
	
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

	@Override
	public int compareTo(CityVo o) {
		char a = this.getInitials().toCharArray()[0];
		char b = o.getInitials().toCharArray()[0];
		return Integer.compare(a, b);
	}
	
}
