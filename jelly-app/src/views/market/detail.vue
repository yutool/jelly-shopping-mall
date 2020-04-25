<template>
  <div class="container">
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
          <p>价格：{{ checkSku.price }} 优惠价：{{ checkSku.price }}</p>
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
          <span>数量: </span>
          <el-input-number size="mini" v-model="checkSku.num"></el-input-number>
          <span>剩余库存：{{ checkSku.residue }}</span>
        </div>
        <div class="goods-buy">
          <el-button @click="buy">立即购买</el-button>
          <el-button @click="addCart">加入购物车</el-button>
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
import { getGoods } from '@/api/spu'
import { addCart } from '@/api/cart'
import { copyObj } from '@/common/utils/ObjectUtil'

@Component
export default class GoodsDetail extends Vue {
  private spu: any = {}
  private skuList: any = []
  private checkList: any = {}  // 选中的属性
  private checkSku = {
    id: '',
    image: '',
    price: '',  // 显示的价格
    residue: 0, // 剩余库存个数
    num: 0      // 选择的个数
  }
 
  // 计算选中的sku
  private calculate() {
    this.checkSku.residue = 0
    const cKeys = Object.keys(this.checkList)
    // 计算剩余库存
    for (const sku of this.skuList) {
      const skuVals = Object.values(sku.sku)
      this.checkSku.residue += sku.num
      for (const c of cKeys) {
        if (skuVals.indexOf(this.checkList[c]) !== -1) {
          continue
        }
        this.checkSku.residue -= sku.num
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
          this.checkSku.image = item.image
          this.checkSku.price = item.price
          this.checkSku.id = item.id
          if (this.checkSku.num > item.num) {
            this.checkSku.num = item.num
          }
          break
        }
      }
    }
  }
  
  // 加入购物车
  private addCart() {
    if ( this.checkSku.id === '') {
      this.$message({ type: 'info', message: '暂无该商品' })
      return
    }
    const cart = {
      skuId: this.checkSku.id,
      userId: '0',  // 待修改
      name: this.spu.title,
      price: this.checkSku.price,
      num: this.checkSku.num
    };
    addCart(cart).then((res: any) => {
      this.$message({ type: 'success', message: '加入购物车成功' })
      this.$log.info('加购', res)
    })
  }
  
  // 购买
  private buy() {
    if ( this.checkSku.id === '') {
      this.$message({ type: 'info', message: '暂无该商品' })
      return
    }
    const order: any = []
    const orderItem = {
      id: this.checkSku.id,
      name: this.spu.title,
      image: this.checkSku.image,
      sku: this.checkList,
      price: this.checkSku.price,
      num: this.checkSku.num
    }
    order.push(orderItem)
    console.log(order)
    this.$router.push({
      name: 'buy', 
      params: { order }
    })
  }
   
  // 初始化商品信息
  private initGoods() {
    getGoods(this.$route.params.id).then((res: any) => {
      const { spu, sku } = res.data
      this.spu = spu
      this.spu.skuTemplate = JSON.parse(spu.skuTemplate)
      this.skuList = sku
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
  