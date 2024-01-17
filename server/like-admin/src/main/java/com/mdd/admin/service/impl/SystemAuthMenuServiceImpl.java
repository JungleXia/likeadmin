package com.mdd.admin.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.mdd.admin.LikeAdminThreadLocal;
import com.mdd.admin.service.ISystemAuthMenuService;
import com.mdd.admin.service.ISystemAuthPermService;
import com.mdd.admin.validate.system.SystemMenuCreateValidate;
import com.mdd.admin.validate.system.SystemMenuUpdateValidate;
import com.mdd.admin.vo.system.SystemAuthMenuVo;
import com.mdd.common.entity.system.SystemAuthMenu;
import com.mdd.common.mapper.system.SystemAuthMenuMapper;
import com.mdd.common.util.ListUtils;
import com.mdd.common.util.TimeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 系统菜单服务实现类
 */
@Service
public class SystemAuthMenuServiceImpl implements ISystemAuthMenuService {

    @Resource
    SystemAuthMenuMapper systemAuthMenuMapper;

    @Resource
    ISystemAuthPermService iSystemAuthPermService;

    /**
     * 根据角色ID获取菜单
     *
     * @author fzr
     * @param roleIds 角色ID
     * @return JSONArray
     */
    @Override
    public JSONArray selectMenuByRoleId(List<Integer> roleIds) {
        Integer adminId = LikeAdminThreadLocal.getAdminId();
        List<Integer> menuIds = iSystemAuthPermService.selectMenuIdsByRoleId(roleIds);

        QueryWrapper<SystemAuthMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("menu_type", Arrays.asList("M", "C"));
        queryWrapper.eq("is_disable", 0);
        queryWrapper.orderByDesc("menu_sort");
        queryWrapper.orderByAsc("id");
        if (!adminId.equals(1)) {
            if (menuIds.size() <= 0) {
                menuIds.add(0);
            }
            queryWrapper.in("id", menuIds);
        }

        List<SystemAuthMenu> systemAuthMenus = systemAuthMenuMapper.selectList(queryWrapper);

        List<SystemAuthMenuVo> lists = new ArrayList<>();
        for (SystemAuthMenu systemAuthMenu : systemAuthMenus) {
            SystemAuthMenuVo vo = new SystemAuthMenuVo();
            BeanUtils.copyProperties(systemAuthMenu, vo);

            vo.setUpdateTime(TimeUtils.timestampToDate(systemAuthMenu.getUpdateTime()));
            vo.setCreateTime(TimeUtils.timestampToDate(systemAuthMenu.getCreateTime()));
            lists.add(vo);
        }

        JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSONString(lists));
        return ListUtils.listToTree(jsonArray, "id", "pid", "children");
    }

    /**
     * 菜单列表
     *
     * @author fzr
     * @return JSONArray
     */
    @Override
    public JSONArray list() {
        QueryWrapper<SystemAuthMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("menu_sort");
        queryWrapper.orderByAsc("id");

        List<SystemAuthMenu> systemAuthMenus = systemAuthMenuMapper.selectList(queryWrapper);

        List<SystemAuthMenuVo> lists = new ArrayList<>();
        for (SystemAuthMenu systemAuthMenu : systemAuthMenus) {
            SystemAuthMenuVo vo = new SystemAuthMenuVo();
            BeanUtils.copyProperties(systemAuthMenu, vo);

            vo.setCreateTime(TimeUtils.timestampToDate(systemAuthMenu.getCreateTime()));
            vo.setUpdateTime(TimeUtils.timestampToDate(systemAuthMenu.getUpdateTime()));
            lists.add(vo);
        }

        JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSONString(lists));
        return ListUtils.listToTree(jsonArray, "id", "pid", "children");
    }

    /**
     * 菜单详情
     *
     * @author fzr
     * @param id 主键参数
     * @return SysMenu
     */
    @Override
    public SystemAuthMenuVo detail(Integer id) {
        SystemAuthMenu systemAuthMenu = systemAuthMenuMapper.selectOne(new QueryWrapper<SystemAuthMenu>().eq("id", id));
        Assert.notNull(systemAuthMenu, "菜单已不存在!");

        SystemAuthMenuVo vo  = new SystemAuthMenuVo();
        BeanUtils.copyProperties(systemAuthMenu, vo);
        vo.setCreateTime(TimeUtils.timestampToDate(systemAuthMenu.getCreateTime()));
        vo.setUpdateTime(TimeUtils.timestampToDate(systemAuthMenu.getUpdateTime()));

        return vo;
    }

    /**
     * 新增菜单
     *
     * @author fzr
     * @param createValidate 参数
     */
    @Override
    public void add(SystemMenuCreateValidate createValidate) {
        SystemAuthMenu model = new SystemAuthMenu();
        model.setPid(createValidate.getPid());
        model.setMenuType(createValidate.getMenuType());
        model.setMenuName(createValidate.getMenuName());
        model.setMenuIcon(createValidate.getMenuIcon());
        model.setMenuSort(createValidate.getMenuSort());
        model.setPerms(createValidate.getPerms());
        model.setPaths(createValidate.getPaths());
        model.setComponent(createValidate.getComponent());
        model.setSelected(createValidate.getSelected());
        model.setParams(createValidate.getParams());
        model.setIsCache(createValidate.getIsCache());
        model.setIsShow(createValidate.getIsShow());
        model.setIsDisable(createValidate.getIsDisable());
        model.setCreateTime(System.currentTimeMillis() / 1000);
        model.setUpdateTime(System.currentTimeMillis() / 1000);
        systemAuthMenuMapper.insert(model);
    }

    /**
     * 编辑菜单
     *
     * @author fzr
     * @param updateValidate 菜单
     */
    @Override
    public void edit(SystemMenuUpdateValidate updateValidate) {
        SystemAuthMenu model = systemAuthMenuMapper.selectOne(new QueryWrapper<SystemAuthMenu>().eq("id", updateValidate.getId()));
        Assert.notNull(model, "菜单已不存在!");

        model.setMenuType(updateValidate.getMenuType());
        model.setMenuName(updateValidate.getMenuName());
        model.setMenuIcon(updateValidate.getMenuIcon());
        model.setMenuSort(updateValidate.getMenuSort());
        model.setPaths(updateValidate.getPaths());
        model.setPerms(updateValidate.getPerms());
        model.setComponent(updateValidate.getComponent());
        model.setPid(updateValidate.getPid());
        model.setSelected(updateValidate.getSelected());
        model.setParams(updateValidate.getParams());
        model.setIsCache(updateValidate.getIsCache());
        model.setIsShow(updateValidate.getIsShow());
        model.setIsDisable(updateValidate.getIsDisable());
        model.setUpdateTime(System.currentTimeMillis() / 1000);
        systemAuthMenuMapper.updateById(model);
    }

    /**
     * 删除菜单
     *
     * @author fzr
     * @param id 主键参数
     */
    @Override
    public void del(Integer id) {
        SystemAuthMenu model = systemAuthMenuMapper.selectOne(
                new QueryWrapper<SystemAuthMenu>()
                        .select("id,pid,menu_name")
                        .eq("id", id)
                        .last("limit 1"));

        Assert.notNull(model, "菜单已不存在!");
        Assert.isNull(systemAuthMenuMapper.selectOne(
                new QueryWrapper<SystemAuthMenu>()
                        .eq("pid", id)
                        .last("limit 1")),
                "请先删除子菜单再操作!");

        systemAuthMenuMapper.deleteById(id);
        iSystemAuthPermService.batchDeleteByMenuId(id);
    }

}
