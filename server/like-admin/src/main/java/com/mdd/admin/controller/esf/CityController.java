package com.mdd.admin.controller.esf;

import com.mdd.admin.mongo.service.ILjEsfStatisticService;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.house.HouseSearchValidate;
import com.mdd.admin.vo.ZtreeVo;
import com.mdd.common.core.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JungleXia
 * @version 1.0
 * @title HouseController
 * @description 城市列表
 * @date 2024/1/18 13:32:51
 */
@RestController
@RequestMapping("api/esf/city")
@Api(tags = "城市管理")
public class CityController {

    @Resource
    ILjEsfStatisticService iLjEsfStatisticService;

    @GetMapping("/list")
    @ApiOperation(value="城市列表")
    public AjaxResult<List<ZtreeVo>> list(@Validated PageValidate pageValidate) {
        List<ZtreeVo> ztree = iLjEsfStatisticService.selectZtreeData();
        return AjaxResult.success(ztree);
    }


}
