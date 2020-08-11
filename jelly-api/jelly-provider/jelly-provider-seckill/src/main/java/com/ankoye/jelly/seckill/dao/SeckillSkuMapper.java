package com.ankoye.jelly.seckill.dao;

import com.ankoye.jelly.seckill.domain.SeckillSku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @author ankoye@qq.com
 */
@Mapper
public interface SeckillSkuMapper extends BaseMapper<SeckillSku> {

    /**
     * 扣减库存
     */
    @Update("UPDATE tb_seckill_sku set residue = residue-#{num} WHERE id = #{id}")
    int deductInventory(String id, Integer num);
}
