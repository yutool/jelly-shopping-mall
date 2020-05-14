<template>
  <div class="container" v-loading.fullscreen="loading" element-loading-text="正在抢货，请稍等">
    <el-row :gutter="20">
      <!-- 照片墙 -->
      <el-col :md="12">
        <div class="goods-images">
          <img :src="spu.picture" alt="">
        </div>
      </el-col>
      <!-- 选择商品 -->
      <el-col :md="12">
        <div class="goods-title-sku">
          {{ spu.title }}
        </div>
        <div class="goods-price">
          <p>秒杀价格：{{ checkSku.price }} <s>原价：{{ checkSku.costPrice }}</s></p>
          <p>累计评价: {{ spu.commentNum }} 累计销售：{{ spu.saleNum }}</p>
        </div>
        <!-- SKU -->
         <el-form ref="form" label-width="90px">
          <el-form-item v-for="(value, name) in spu.skuTemplate" :key="name"  :label="name" >
            <el-radio-group v-model="checkList[name]" >
              <el-radio v-for="item in value" :key="item" :label="item" @change="calculate()">
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <div class="goods-amount">
          <span>数量: {{ checkSku.num }} </span>
          <span>剩余库存：{{ checkSku.residue }}</span>
        </div>
        <div class="goods-buy">
          <el-button disabled>加入购物车</el-button>
          <el-button @click="buy">立即购买</el-button>
        </div>
        <div class="extra-services">
          服务说明：<span>{{ spu.serve }}</span>
        </div>
        <div class="extra-pay">
          支付方式：微信
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { Getter } from 'vuex-class'
import { getGoods, queueUp, queryQueue } from '@/api/seckill'
import { getOrder } from '@/api/order'
import { copyObj } from '@/common/utils/ObjectUtil'

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
    costPrice: '', // 原价
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
          this.checkSku.costPrice = item.costPrice
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
      userId: '0',
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
    this.queueTimer = setInterval(() => queryQueue('0', this.checkSku.id).then((res: any) => {
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
      this.checkList = copyObj(this.checkList)
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
.goods-images {
  img {
    width: 90%;
  }
}
.specs-item-choose {
  display: inline-block;
  li {
    display: inline;
    list-style: none;
  }
}
</style>
  