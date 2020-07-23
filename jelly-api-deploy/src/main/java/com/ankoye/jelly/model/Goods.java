package com.ankoye.jelly.model;

import com.ankoye.jelly.domain.Sku;
import com.ankoye.jelly.domain.Spu;
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
