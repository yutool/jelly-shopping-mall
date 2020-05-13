package com.ankoye.jelly.seckill.model;

import com.ankoye.jelly.goods.domain.Spu;
import com.ankoye.jelly.seckill.domain.SeckillSku;
import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
public class SeckillGoods implements Serializable {
    private Spu spu;

    private List<SeckillSku> skuList;

    public SeckillGoods() {
        skuList = new LinkedList<>();
    }
}
