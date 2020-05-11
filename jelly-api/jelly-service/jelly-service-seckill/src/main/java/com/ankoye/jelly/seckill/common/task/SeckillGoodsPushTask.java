package com.ankoye.jelly.seckill.common.task;

import com.ankoye.jelly.seckill.common.constant.RedisKey;
import com.ankoye.jelly.seckill.dao.SeckillGoodsMapper;
import com.ankoye.jelly.seckill.domain.SeckillGoods;
import com.ankoye.jelly.util.DateUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
@SuppressWarnings("all")
@Component
public class SeckillGoodsPushTask {

    @Resource
    private SeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "0/30 * * * * ?")
//    @Scheduled(cron = "0 59 1,3,5,7,9,11 * * ?")
    public void  loadSeckillGoodsToRedis(){
        /**
         * 1.查询所有符合条件的秒杀商品
         * 	1) 获取时间段集合并循环遍历出每一个时间段
         * 	2) 获取每一个时间段名称,用于后续redis中key的设置
         * 	3) 状态必须为审核通过 status=1
         * 	4) 商品库存个数>0
         * 	5) 秒杀商品开始时间>=当前时间段
         * 	6) 秒杀商品结束<当前时间段+2小时
         * 	7) 排除之前已经加载到Redis缓存中的商品数据
         * 	8) 执行查询获取对应的结果集
         * 2.将秒杀商品存入缓存
         */

        List<Date> dateMenus = DateUtils.getDateMenus(); // 获取当前时间菜单 5个

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 循环求每个时间区间的商品
        for (Date dateMenu : dateMenus) {

            String redisExtName = DateUtils.date2Str(dateMenu);

            QueryWrapper<SeckillGoods> wrapper = new QueryWrapper<>();
            wrapper.eq("status", "1");
            wrapper.gt("stockCount", 0);
            wrapper.ge("startTime", simpleDateFormat.format(dateMenu));
            wrapper.lt("endTime", simpleDateFormat.format(DateUtils.addDateHour(dateMenu,2)));

            Set keys = redisTemplate.boundHashOps(RedisKey.SECKILL_GOODS_KEY + redisExtName).keys();//key field value
            if (keys != null && keys.size() > 0){
                wrapper.notIn("id", keys);
            }

            List<SeckillGoods> seckillGoodsList = seckillGoodsMapper.selectList(wrapper);

            // 添加到缓存中
            for (SeckillGoods seckillGoods : seckillGoodsList) {
                redisTemplate.boundHashOps(RedisKey.SECKILL_GOODS_KEY + redisExtName).put(seckillGoods.getId(), seckillGoods);

                //加载秒杀商品的库存
                redisTemplate.opsForValue().set(RedisKey.SECKILL_GOODS_STOCK_COUNT_KEY + seckillGoods.getId(), seckillGoods.getStockCount());
            }
        }

    }
}
