package com.ankoye.jelly.seckill.common.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ankoye.jelly.goods.domain.Spu;
import com.ankoye.jelly.goods.service.SpuService;
import com.ankoye.jelly.seckill.common.constant.RedisKey;
import com.ankoye.jelly.seckill.dao.SeckillGoodsMapper;
import com.ankoye.jelly.seckill.domain.SeckillSku;
import com.ankoye.jelly.seckill.model.SeckillGoods;
import com.ankoye.jelly.util.DateUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author ankoye@qq.com
 */
@SuppressWarnings("all")
@Component
@Slf4j
public class SeckillGoodsPushTask {

    @Reference
    private SpuService spuService;

    @Resource
    private SeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "0/30 * * * * ?") // 测试
//    @Scheduled(cron = "0 59 1,3,5,7,9,11 * * ?")
    public void  loadSeckillGoodsToRedis() {
        /**
         * 1.查询所有符合条件的秒杀商品
         * 	1) 获取时间段集合并循环遍历出每一个时间段
         * 	2) 获取每一个时间段名称,用于后续redis中key的设置
         * 	3) 状态必须为审核通过 status = 0
         * 	4) 商品库存个数 > 0
         * 	5) 在秒杀时间时间范围内
         * 	6) 秒杀商品开始时间  =当前时间段
         * 	7) 排除之前已经加载到Redis缓存中的商品数据
         * 	8) 执行查询获取对应的结果集
         * 	9) 未来：在秒杀前一分钟存入数据，2小时失效
         * 2.将秒杀商品存入缓存
         */

        List<String> dateMenus = DateUtils.getDateMenus(); // 获取5个时间段

        // 循环求每个时间区间的商品
        for (String dateMenu : dateMenus) {
            String day = DateUtils.menuToDay(dateMenu);

            QueryWrapper<SeckillSku> wrapper = new QueryWrapper<>();
            wrapper.eq("status", 0);
            wrapper.eq("is_marketable", 1);
            wrapper.gt("residue", 0);
            wrapper.le("start_time", day);
            wrapper.ge("end_time", day);
            wrapper.eq("region", dateMenu.substring(8));

            Set keys = redisTemplate.boundHashOps(RedisKey.SECKILL_GOODS + dateMenu).keys(); //key field value
            if (keys != null && keys.size() > 0){
                wrapper.notIn("spu_id", keys);
            }

            List<SeckillSku> seckillSkuList = seckillGoodsMapper.selectList(wrapper);

            // version 2
            HashSet<String> spuIdList = new HashSet<>();
            for (SeckillSku tmp : seckillSkuList) {
                if (spuIdList.contains(tmp.getSpuId())) {
                    continue;
                }
                spuIdList.add(tmp.getSpuId());
                // 查询商品Spu，并创建秒杀商品
                Spu spu = spuService.getSpu(tmp.getSpuId());
                SeckillGoods seckillGoods = new SeckillGoods();
                seckillGoods.setSpu(spu);
                // 查找到 当前 spu 对应的秒杀商品添加到
                for (SeckillSku sku : seckillSkuList) {
                    if (sku.getSpuId().equals(spu.getId())) {
                        // 添加秒杀商品 sku
                        seckillGoods.getSkuList().add(sku);
                        // 将库存保存一份至redis
                        redisTemplate.opsForValue().set(RedisKey.SECKILL_SKU_COUNT_KEY + sku.getId(), sku.getResidue());
                    }
                }
                redisTemplate.boundHashOps(RedisKey.SECKILL_GOODS + dateMenu).put(tmp.getSpuId(), seckillGoods);
            }
        }
    }
}
