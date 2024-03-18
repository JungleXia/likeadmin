package com.mdd.admin.mongo.service.impl;

import com.mdd.admin.mongo.entity.LjEsfHouse;
import com.mdd.admin.mongo.service.ILjEsfHouseService;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.house.HouseOrderByValidate;
import com.mdd.admin.validate.house.HouseSearchValidate;
import com.mdd.common.core.PageResult;
import com.mdd.common.util.MapUtils;
import com.mdd.common.util.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class LjEsfHouseServiceImpl extends BaseMongoServiceImpl<LjEsfHouse> implements ILjEsfHouseService {

    @Override
    public PageResult<LjEsfHouse> list(PageValidate pageValidate, HouseSearchValidate searchValidate, HouseOrderByValidate houseOrderByValidate) {
        List<Sort.Order> orders =new ArrayList<>();
        if (houseOrderByValidate != null) {
            Map<String, String> sortMap = MapUtils.objectToMap(houseOrderByValidate);
            for (Map.Entry<String, String> entry : sortMap.entrySet()) {
                String sortKey = entry.getKey();
                String sortValue = entry.getValue();
                if (StringUtils.isNotEmpty(sortValue)) {
                    if (StringUtils.equals(sortValue, "descending")) {
                        orders.add(new Sort.Order(Sort.Direction.DESC, sortKey.replace("Sort", "")));
                    } else if (StringUtils.equals(sortValue, "ascending")) {
                        orders.add(new Sort.Order(Sort.Direction.ASC, sortKey.replace("Sort", "")));
                    }
                }
            }
        } else {
            orders.add(new Sort.Order(Sort.Direction.DESC, "updateTime"));
        }
        PageRequest pageReq = PageRequest.of(pageValidate.getPageNo() - 1, pageValidate.getPageSize(), Sort.by(orders));
        return findPage(pageReq, searchValidate);
    }
}
