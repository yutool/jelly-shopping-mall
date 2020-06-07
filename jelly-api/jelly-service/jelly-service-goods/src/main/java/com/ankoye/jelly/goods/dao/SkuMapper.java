package com.ankoye.jelly.goods.dao;

import com.ankoye.jelly.goods.domain.Sku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SkuMapper extends BaseMapper<Sku> {
    /**
     * 冻结库存
     */
    @Update("UPDATE tb_sku SET num = num-#{num}, freeze_num=freeze_num+#{num} " +
            " WHERE id = #{id} and num >= #{num}")
    int freezeScore(@Param("id") String id, @Param("num") Integer num);

    /**
     * 付款成功，删除冻结库存
     */
    @Update("UPDATE tb_sku SET freeze_num = freeze_num-#{num} WHERE id = #{id}")
    int decreaseScore(@Param("id") String id, @Param("num") Integer num);

    /**
     * 取消订单，解冻
     */
    @Update("UPDATE tb_sku SET num = num+#{num}, freeze_num=freeze_num-#{num} " +
            " WHERE id = #{id}")
    int unfreezeScore(@Param("id") String id, @Param("num") Integer num);

    /**
     * 增加销量
     */
    @Update("UPDATE tb_sku SET sale_num = sale_num + #{num} WHERE id = #{id}")
    int addSaleNum(@Param("id") String id, @Param("num") Integer num);
}
