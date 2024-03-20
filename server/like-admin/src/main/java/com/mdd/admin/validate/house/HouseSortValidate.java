package com.mdd.admin.validate.house;

import lombok.Data;

/**
 * @author JungleXia
 * @version 1.0
 * @title HouseOrderByValidate
 * @description 房源搜索排序
 * @date 2024/3/18 13:47:33
 */
@Data
public class HouseSortValidate extends SortValidate {

    private static final long serialVersionUID = 1L;

    private String totalPriceSort;

    private String unitPriceSort;

    private String diffSort;

    private String diffRateSort;
}
