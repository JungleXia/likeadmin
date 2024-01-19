package com.mdd.admin.controller.esf;

import com.mdd.admin.aop.Log;
import com.mdd.admin.mongo.entity.LjEsfHouse;
import com.mdd.admin.mongo.service.ILjEsfHouseService;
import com.mdd.admin.service.IArticleService;
import com.mdd.admin.validate.article.ArticleCreateValidate;
import com.mdd.admin.validate.article.ArticleSearchValidate;
import com.mdd.admin.validate.article.ArticleUpdateValidate;
import com.mdd.admin.validate.commons.IdValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.house.HouseSearchValidate;
import com.mdd.admin.vo.article.ArticleDetailVo;
import com.mdd.admin.vo.article.ArticleListedVo;
import com.mdd.admin.vo.house.HouseListedVo;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.core.PageResult;
import com.mdd.common.validator.annotation.IDMust;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author JungleXia
 * @version 1.0
 * @title HouseController
 * @description 二手房源
 * @date 2024/1/18 13:32:51
 */
@RestController
@RequestMapping("api/esf/house")
@Api(tags = "二手房源管理")
public class HouseController {

    @Resource
    ILjEsfHouseService iLjEsfHouseService;

    @GetMapping("/list")
    @ApiOperation(value="房源列表")
    public AjaxResult<PageResult<LjEsfHouse>> list(@Validated PageValidate pageValidate,
                                             @Validated HouseSearchValidate searchValidate) {
        PageResult<LjEsfHouse> vos = iLjEsfHouseService.list(pageValidate, searchValidate);
        return AjaxResult.success(vos);
    }

    @GetMapping("/detail")
    @ApiOperation(value="房源详情")
    public AjaxResult<LjEsfHouse> detail(@Validated @IDMust() @RequestParam("id") String id) {
        LjEsfHouse vo = iLjEsfHouseService.getById(id);
        return AjaxResult.success(vo);
    }


    @Log(title = "房源删除")
    @PostMapping("/del")
    @ApiOperation(value="房源删除")
    public AjaxResult<Object> del(@Validated @RequestParam("id") String id) {
        iLjEsfHouseService.delete(id);
        return AjaxResult.success();
    }

}
