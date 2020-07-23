package com.ankoye.jelly.dao;

import com.ankoye.jelly.domain.Spu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author ankoye@qq.com
 */
@Mapper
public interface SpuMapper extends BaseMapper<Spu> {

    /**
     * 增加销售量
     */
    @Update("UPDATE tb_spu SET sale_num = sale_num + #{num} WHERE id = #{id}")
    int addSaleNum(@Param("id") String id, @Param("num") Integer num);
}
