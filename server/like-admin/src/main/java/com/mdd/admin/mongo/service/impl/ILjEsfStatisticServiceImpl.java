package com.mdd.admin.mongo.service.impl;

import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.mdd.admin.mongo.entity.LjEsfStatistic;
import com.mdd.admin.mongo.service.ILjEsfStatisticService;
import com.mdd.admin.vo.ZtreeVo;
import com.mdd.admin.vo.house.CityVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;



@Service
public class ILjEsfStatisticServiceImpl extends BaseMongoServiceImpl<LjEsfStatistic> implements ILjEsfStatisticService {

    @Cacheable(value = "statistic",key = "'city_list_#showDistrict_#showBlock'",unless = "#result == null or #result.size() == 0")
	@Override
	public List<CityVo> findCityList(boolean showDistrict, boolean showBlock) {
    	//city
    	List<LjEsfStatistic> list = findByProp("type", 1, "city_key 1");
    	List<CityVo> result = new ArrayList<>();
    	for (LjEsfStatistic city : list) {
    		CityVo model = new CityVo();
    		model.setInitials(String.valueOf(PinyinHelper.getShortPinyin(city.getCity()).toUpperCase().charAt(0)));
    		model.setExpaned(false);
    		model.setName(city.getCity());
    		model.setNumbers(city.getNumbers());
    		model.setProvince(city.getProvince());
    		model.setCreateDay(city.getCreateDay());
    		model.setDiff(city.getDiff());
    		model.setPrenums(city.getPrenums());

			if (showDistrict) {
				// district
				List<CityVo> subList = new ArrayList<>();
				List<LjEsfStatistic> districtlist = findByProps(new String[]{"city", "type" }, new Object[]{city.getCity(), 2});
				for (LjEsfStatistic district : districtlist) {
					CityVo districtModel = new CityVo();
					districtModel.setInitials(String.valueOf(PinyinHelper.getShortPinyin(district.getDistrict()).toUpperCase().charAt(0)));
					districtModel.setExpaned(false);
					districtModel.setName(district.getDistrict());
					districtModel.setNumbers(district.getNumbers());
					districtModel.setCreateDay(district.getCreateDay());
					districtModel.setDiff(district.getDiff());
					districtModel.setPrenums(district.getPrenums());

					if (showBlock) {
						// block
						List<CityVo> blockList = new ArrayList<>();
						List<LjEsfStatistic> blockStatisticList = findByProps(new String[]{"city", "district", "type" }, new Object[]{district.getCity(), district.getDistrict(), 3});
						for (LjEsfStatistic block : blockStatisticList) {
							CityVo blockModel = new CityVo();
							blockModel.setInitials(String.valueOf(PinyinHelper.getShortPinyin(block.getBlock()).toUpperCase().charAt(0)));
							blockModel.setExpaned(false);
							blockModel.setName(block.getBlock());
							blockModel.setNumbers(block.getNumbers());
							blockModel.setCreateDay(block.getCreateDay());
							blockModel.setDiff(block.getDiff());
							blockModel.setPrenums(block.getPrenums());

							blockList.add(blockModel);
						}
						// 按首字母排序
						Collections.sort(blockList);
						districtModel.setSubList(blockList);
					}
					subList.add(districtModel);
				}
				model.setSubList(subList);
				// 按首字母排序
				Collections.sort(subList);
			}
    		result.add(model);
		}
    	
    	// 按首字母排序
    	Collections.sort(result);
    	return result;
	}

    @Cacheable(value = "statistic",key = "'province_list'",unless = "#result == null or #result.size() == 0")
	@Override
	public List<CityVo> findProvinceList() {
    	List<CityVo> result = new ArrayList<>();
    	List<CityVo> cityList = findCityList(false, false);
    	Set<String> provinceSet = new HashSet<>();
    	for (CityVo CityVo : cityList) {
    		provinceSet.add(CityVo.getProvince());
		}
    	for (String province : provinceSet) {
    		CityVo model = new CityVo();
    		model.setInitials(PinyinHelper.getShortPinyin(province).toUpperCase());
    		model.setExpaned(false);
    		model.setName(province);
    		model.setProvince(province);
    		List<CityVo> subList = new ArrayList<>();
			for (CityVo cityVo : cityList) {
				if (StringUtils.equals(province, cityVo.getProvince())) {
					subList.add(cityVo);
				}
			}
			model.setSubList(subList);
			
			result.add(model);
		}
    	// 按首字母排序
    	result.sort(new Comparator<CityVo>() {
    		@Override
    		public int compare(CityVo model1, CityVo model2) {
    			char a = model1.getInitials().toCharArray()[0];
    			char b = model2.getInitials().toCharArray()[0];
    			return Integer.compare(a, b);
    		}
    		
		});
    	return result;
	}

    @Cacheable(value = "statistic",key = "'province_ztree'",unless = "#result == null or #result.size() == 0")
	@Override
	public List<ZtreeVo> selectZtreeData() {
    	List<CityVo> provinceList = findProvinceList();
		return changeZtree(1L, provinceList);
	}
    
    
    public List<ZtreeVo> changeZtree(Long parentId, List<CityVo> modelList) {
    	List<ZtreeVo> result = new ArrayList<>();
    	if (modelList != null && modelList.size() > 0) {
    		int idStart = Integer.parseInt(String.valueOf(parentId) + "01");
    		for (int i = 0; i < modelList.size(); i++) {
        		List<CityVo> cityList = modelList.get(i).getSubList();
        		ZtreeVo province = new ZtreeVo();
        		province.setId((long) (idStart + i));
				province.setValue(modelList.get(i).getName());
        		province.setLabel(modelList.get(i).getName());
        		province.setOpen(false);
        		province.setPid(parentId);
        		if (cityList != null && cityList.size() > 0) {
            		province.setIsParent(true);
              		province.setChildren(changeZtree(province.getId(), cityList));
        		} else {
            		province.setIsParent(false);
        		}
          		result.add(province);
			}
    	}
    	return result;
    }
}
