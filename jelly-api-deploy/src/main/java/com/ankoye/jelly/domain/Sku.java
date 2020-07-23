package com.ankoye.jelly.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ankoye@qq.com
 */
@Data
@TableName("tb_sku")
public class Sku implements Serializable {
    private String id;

    private String spuId;

    private String sku;

    private BigDecimal price;

    private Float discount;

    private Integer num;

    private Integer alertNum;

    private String image;

    private Integer saleNum;

    private Date createTime;

    private Date updateTime;

    private Integer freezeNum;
}