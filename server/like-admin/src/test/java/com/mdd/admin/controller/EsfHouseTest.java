package com.mdd.admin.controller;

import com.mdd.admin.mongo.entity.LjEsfHouse;
import com.mdd.admin.mongo.service.ILjEsfHouseService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author JungleXia
 * @version 1.0
 * @title EsfHouseTest
 * @description 二手房 mongodb test
 * @date 2024/1/18 15:42:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class EsfHouseTest {

    @Resource
    ILjEsfHouseService iLjEsfHouseService;

    @Test
    public void test() {
        LjEsfHouse house = iLjEsfHouseService.getById("65a8e48789d8c59220f4aa96");
        System.out.println(house.getHouseNo());
        Assert.assertEquals("105115793707", house.getHouseNo());
    }

}
