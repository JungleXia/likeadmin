package com.mdd.admin.mongo.service;


import com.mdd.admin.mongo.entity.LjEsfHouse;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.house.HouseSearchValidate;
import com.mdd.admin.vo.house.HouseListedVo;
import com.mdd.common.core.PageResult;
import org.springframework.data.domain.Page;

public interface ILjEsfHouseService extends BaseMongoService<LjEsfHouse> {
    /**
     * 房源列表
     *
     * @param pageValidate   分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<ArticleListVo>
     * @author fzr
     */
    PageResult<LjEsfHouse> list(PageValidate pageValidate, HouseSearchValidate searchValidate);
}