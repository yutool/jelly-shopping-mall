package com.ankoye.jelly.goods.model;

import com.ankoye.jelly.goods.domain.Sku;
import com.ankoye.jelly.goods.domain.Spu;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ankoye@qq.com
 */
@Data
@AllArgsConstructor
public class Goods implements Serializable {
    private Spu spu;

    private List<Sku> sku;
}
