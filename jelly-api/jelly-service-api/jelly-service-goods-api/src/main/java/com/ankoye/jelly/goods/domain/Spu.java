package com.ankoye.jelly.goods.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("tb_spu")
public class Spu implements Serializable {
    private String id;

    private String name;

    private String title;

    private Integer merchantId;

    private String brand;

    private Integer category1Id;

    private Integer category2Id;

    private Integer category3Id;

    private String picture;

    private String price;

    private String details;

    private String specs;

    private String serve;

    private String skuTemplate;

    private Integer saleNum;

    private Integer commentNum;

    private Integer seq;

    private Boolean isMarketable;

    private Integer status;

}