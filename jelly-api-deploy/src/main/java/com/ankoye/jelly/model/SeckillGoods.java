package com.ankoye.jelly.model;

import com.ankoye.jelly.domain.SeckillSku;
import com.ankoye.jelly.domain.Spu;
import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ankoye@qq.com
 */
@Data
public class SeckillGoods implements Serializable {
    private Spu spu;

    private List<SeckillSku> skuList;

    public SeckillGoods() {
        skuList = new LinkedList<>();
    }
}
