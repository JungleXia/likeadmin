package com.mdd.admin.controller.esf;

import com.mdd.admin.aop.Log;
import com.mdd.admin.mongo.entity.LjEsfCommunity;
import com.mdd.admin.mongo.entity.LjEsfCommunityHistory;
import com.mdd.admin.mongo.service.ILjEsfCommunityHistoryService;
import com.mdd.admin.mongo.service.ILjEsfCommunityService;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.house.CommunitySearchValidate;
import com.mdd.admin.validate.house.CommunitySortValidate;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.core.PageResult;
import com.mdd.common.validator.annotation.IDMust;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author JungleXia
 * @version 1.0
 * @title CommunityController
 * @description 小区数据
 * @date 2024/1/18 18:11:23
 */
@RestController
@RequestMapping("api/esf/community")
@Api(tags = "小区管理")
public class CommunityController {

    @Resource
    ILjEsfCommunityService iLjEsfCommunityService;

    @Resource
    ILjEsfCommunityHistoryService iLjEsfCommunityHistoryService;

    @GetMapping("/list")
    @ApiOperation(value="小区列表")
    public AjaxResult<PageResult<LjEsfCommunity>> list(@Validated PageValidate pageValidate,
                                                       @Validated CommunitySearchValidate searchValidate,
                                                       @Validated CommunitySortValidate sortValidate) {
        PageResult<LjEsfCommunity> vos = iLjEsfCommunityService.list(pageValidate, searchValidate, sortValidate);
        return AjaxResult.success(vos);
    }

    @GetMapping("/detail")
    @ApiOperation(value="小区详情")
    public AjaxResult<LjEsfCommunity> detail(@Validated @IDMust() @RequestParam("id") String id) {
        LjEsfCommunity vo = iLjEsfCommunityService.getById(id);
        return AjaxResult.success(vo);
    }

    @GetMapping("/history")
    @ApiOperation(value="小区历史")
    public AjaxResult<List<LjEsfCommunityHistory>> history(@Validated @NotNull @RequestParam("cNo") String cNo) {
        List<LjEsfCommunityHistory> list = iLjEsfCommunityHistoryService.findByProp("cNo", cNo, "id desc");
        return AjaxResult.success(list);
    }

    @Log(title = "小区删除")
    @PostMapping("/del")
    @ApiOperation(value="小区删除")
    public AjaxResult<Object> del(@Validated @RequestParam("id") String id) {
        iLjEsfCommunityService.delete(id);
        return AjaxResult.success();
    }

}
