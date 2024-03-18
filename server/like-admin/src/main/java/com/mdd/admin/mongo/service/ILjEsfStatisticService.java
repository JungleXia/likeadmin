package com.mdd.admin.mongo.service;

import java.util.List;

import com.mdd.admin.mongo.entity.LjEsfStatistic;
import com.mdd.admin.vo.ZtreeVo;
import com.mdd.admin.vo.house.CityVo;


public interface ILjEsfStatisticService extends BaseMongoService<LjEsfStatistic>{

	/**
	 * 获取城市列表 市-区-街道
	 * @return
	 */
	List<CityVo> findCityList(boolean showDistrict, boolean showBlock);

	/**
	 * 获取省份列表 省-市
	 * @return
	 */
	List<CityVo> findProvinceList();

	/**
	 * 获取树形结构-城市列表：省-市-区-街道
	 * @return
	 */
	List<ZtreeVo> selectZtreeData();
}
