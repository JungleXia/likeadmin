package com.mdd.admin.mongo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 链家二手房小区历史信息
 */
@Data
@Document(collection = "lj_esf_community_his")
public class LjEsfCommunityHistory implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7008633486089835446L;

    @Id
    private String id;
    /**
     * 编号
     */
    private String cNo;
    /**
     * 在售房源数量
     */
    private Integer numbers;
    /**
     * 小区均价
     */
    private BigDecimal unitPrice;
    /**
     * dealDay内成交数量
     */
    private Integer dealNum;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 房源数量变化
     */
    private BigDecimal diffNum;
    /**
     * 均价涨跌
     */
    private BigDecimal diffPrice;
}
