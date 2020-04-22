package com.ankoye.jelly.goods.domain;

import java.io.Serializable;
import java.util.Date;

public class Sku implements Serializable {
    private String id;

    private String spuId;

    private Long price;

    private Float discount;

    private Integer num;

    private Integer alertNum;

    private String image;

    private Integer saleNum;

    private String specs;

    private Date createTime;

    private Date updateTime;
}