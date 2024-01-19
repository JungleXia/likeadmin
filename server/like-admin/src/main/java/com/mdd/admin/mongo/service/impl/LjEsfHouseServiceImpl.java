package com.mdd.admin.mongo.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.google.gson.reflect.TypeToken;
import com.mdd.admin.mongo.entity.LjEsfHouse;
import com.mdd.admin.mongo.service.ILjEsfHouseService;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.house.HouseSearchValidate;
import com.mdd.admin.vo.house.HouseListedVo;
import com.mdd.common.core.PageResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Map;


@Service
public class LjEsfHouseServiceImpl extends BaseMongoServiceImpl<LjEsfHouse> implements ILjEsfHouseService {

    @Override
    public PageResult<LjEsfHouse> list(PageValidate pageValidate, HouseSearchValidate searchValidate) {
        PageRequest pageReq = PageRequest.of(pageValidate.getPageNo() - 1, pageValidate.getPageSize(),
            Sort.by(Sort.Direction.DESC, "updateTime"));
        return findPage(pageReq, searchValidate);
    }
}
