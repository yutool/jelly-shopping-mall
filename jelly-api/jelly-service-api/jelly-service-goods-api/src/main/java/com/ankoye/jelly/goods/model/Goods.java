package com.ankoye.jelly.goods.model;

import com.ankoye.jelly.goods.domain.Sku;
import com.ankoye.jelly.goods.domain.Spu;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Goods {
    private Spu spu;

    private List<Sku> sku;
}
