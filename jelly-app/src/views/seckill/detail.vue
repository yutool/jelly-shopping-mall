<template>
  <div class="container pt-3" v-loading.fullscreen="loading" element-loading-text="正在抢货，请稍等">
    <el-row :gutter="20">
      <!-- 照片墙 -->
      <el-col :md="12">
        <div class="sku-images">
          <img :src="checkSku.image || spu.picture" alt="">
        </div>
      </el-col>
      <!-- 选择商品 -->
      <el-col :md="12">
        <!-- 标题 -->
        <div class="sku-title">
          <el-tag type="danger" size="mini">秒杀</el-tag>
          {{ spu.title }}
        </div>
        <!-- 价格 -->
         <div class="summary-price-wrap">
          <div class="sku-price">
            <span>
              秒杀价：<span class="price">￥{{ checkSku.price }}</span>
            </span>
            <s>原价：￥{{ checkSku.originalPrice }} </s>
          </div>
          <div class="sku-sales">
            <small class="pr-3">累计评价: {{ spu.commentNum }}</small>
            <small>累计销售：{{ spu.saleNum }}</small>
          </div>
        </div>
        <!-- SKU -->
         <el-form ref="form" label-width="80px" label-position="left" class="sku-form">
          <el-form-item v-for="(value, name) in spu.skuTemplate" :key="name"  :label="name" >
            <el-radio-group v-model="checkList[name]" >
              <el-radio v-for="item in value" :key="item" :label="item" @change="calculate()">
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <!-- 立即秒杀 -->
        <div class="sku-buy">
          <el-button type="danger" @click="buy" plain>立即秒杀</el-button>
          <small class="text-muted pl-3">剩余库存：{{ checkSku.residue }}</small>
        </div>
        <!-- 服务 -->
        <div class="sku-serve">
          <span class="sku-form-lable">服务说明：</span>
          {{ spu.serve ? spu.serve : '暂无' }}
        </div>
        <div class="extra-pay">
          <span class="sku-form-lable">支付方式：</span>
          <div class="fl list list-nomaibei"></div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { Getter } from 'vuex-class'
import { getGoods, queueUp, queryQueue } from '@/api/seckill/seckill'
import { getOrder } from '@/api/order/order'
import { copyOf } from '@/common/utils/ObjectUtil'

@Component
export default class GoodsDetail extends Vue {
  @Getter('userId') private userId: any
  private queueTimer: any
  private loading = false
  private spu: any = {}
  private skuList: any = []
  private checkList: any = {}  // 选中的属性
  private checkSku: any = {
    id: '',
    image: '',
    originalPrice: '', // 原价
    price: '',  // 显示的价格
    residue: 0, // 剩余库存个数
    num: 1      // 选择的个数，暂时只能一个
  }
 
  // 计算选中的sku
  private calculate() {
    this.checkSku.residue = 0
    const cKeys = Object.keys(this.checkList)
    // 计算剩余库存
    for (const sku of this.skuList) {
      const skuVals = Object.values(sku.sku)
      this.checkSku.residue += sku.residue
      for (const c of cKeys) {
        if (skuVals.indexOf(this.checkList[c]) !== -1) {
          continue
        }
        this.checkSku.residue -= sku.residue
        break
      }
    }
    // 重置sku部分信息
    this.checkSku.id = ''
    this.checkSku.price = this.spu.price
    this.checkSku.image = this.spu.image
    // 如果存在sku，则设置信息
    if (cKeys.length === Object.keys(this.spu.skuTemplate).length) {
      for (const item of this.skuList) {
        if (JSON.stringify(this.checkList) === JSON.stringify(item.sku)) {  // 找到选中的sku
          this.checkSku.id = item.id
          this.checkSku.image = item.image
          this.checkSku.price = item.price
          this.checkSku.originalPrice = item.originalPrice
          if (this.checkSku.num > item.num) {
            this.checkSku.num = item.num
          }
          break
        }
      }
    }
  }
  
  // 购买
  private buy() {
    if ( this.checkSku.id === '') {
      this.$message({ type: 'info', message: '暂无该商品' })
      return
    }
    const form = {  // 排队
      skuId:  this.checkSku.id,
      userId: this.userId,
      time: this.$route.params.time,
      spuId: this.$route.params.id
    }
    queueUp(form).then((res: any) => {
      console.log(res)
      if (res.code === 0) { // 排队成功
        this.handlerQueue()
      } else {
        this.$message({ type: 'info', message: res.message })
      }
    })
  }
  
  // 排队成功
  private handlerQueue() {
    this.loading = true
    this.queueTimer = setInterval(() => queryQueue(this.userId, this.checkSku.id).then((res: any) => {
      console.log(res)
      if (res.data.status === 3) { // 预创建订单成功
        window.clearInterval(this.queueTimer)
        // 查询订单去付款
        this.$router.push(`/order/buy/${res.data.orderId}`)
      } else if (res.data.status === 2) {
        window.clearInterval(this.queueTimer)
        this.loading = false
        this.$message({ type: 'info', message: '哎呀，商品被抢空了' })
      }
    }), 1500)
  }
   
  // 初始化商品信息
  private initGoods() {
    getGoods(this.$route.params.time, this.$route.params.id).then((res: any) => {
      console.log(res)
      const { spu, skuList } = res.data
      this.spu = spu
      this.spu.skuTemplate = JSON.parse(spu.skuTemplate)
      this.skuList = skuList
      for (const item of this.skuList) {
        item.sku = JSON.parse(item.sku)
      }
      // 默认选中，可优化 库存为0跳过
      for (const item of Object.keys(spu.skuTemplate)) {
        this.checkList[item] = spu.skuTemplate[item][0]
      }
      this.checkList = copyOf(this.checkList)
      // 计算
      this.calculate()
      this.$log.info('查询成功', res)
    })
  }
  
  private mounted() {
    this.initGoods()
  }
  private beforeDestroy() {
    if (this.queueTimer) {
      window.clearInterval(this.queueTimer)
    }
  }
}
</script>

<style scoped lang="scss">
.sku-images {
  text-align: center;
  padding: 10px;
  img {
    width: 90%;
  }
}
.sku-title {
  font: 700 16px Arial,"microsoft yahei";
  color: #666;
  padding-top: 10px;
  line-height: 28px;
  margin-bottom: 5px;
}
.summary-price-wrap {
  padding: 10px;
  background: #f3f3f3;
  .sku-price {
    padding-bottom: 10px;
    .price {
      color: #e4393c;
      font-size: 20px;
      font-weight: 700;
      padding-right: 20px;
    }
  }
}
.sku-form {
  padding: 10px 0;
  .el-form-item {
    margin: 0;
  }
}
.sku-buy {
  padding-bottom: 10px;
}
.sku-serve {
  padding-bottom: 10px;
}
.extra-pay  {
  .list-nomaibei {
    background-position: -37px -295px;
  }
  .list {
    background-image: url(https://s10.mogucdn.com/p2/170122/117603130_6a8jgjh899bh5jd6hcia3ehhj0113_221x704.png);
    background-position: -10px -290px;
    display: inline-block;
    width: 131px;
    height: 25px;
  }
}
.sku-form-lable {
  font-size: 14px;
  color: #606266;
}
</style>
  