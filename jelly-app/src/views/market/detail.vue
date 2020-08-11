<template>
  <div class="container pt-3">
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
          <el-tag size="mini">新品</el-tag>
          {{ spu.title }}
        </div>
        <!-- 价格 -->
        <div class="summary-price-wrap">
          <div class="sku-price">
            <span>
              优惠价：<span class="price">￥{{ checkSku.price }}</span>
            </span>
            <s>原价：￥{{ checkSku.price }} </s>
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
        <!-- 商品数量 -->
        <div class="sku-amount">
          <span class="pr-3">
            <span class="sku-form-lable">
              数量：
            </span>
            <el-input-number :min="1" :max="999" :precision="0" size="mini" v-model="checkSku.num">
            </el-input-number>
          </span>
          <small class="text-muted">剩余库存：{{ checkSku.residue }}</small>
        </div>
        <!-- 操作按钮 -->
        <div class="sku-buy">
          <el-button type="success" @click="buy" plain>立即购买</el-button>
          <el-button type="primary" @click="addCart" plain>加入购物车</el-button>
        </div>
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
import { getGoods } from '@/api/goods/spu'
import { addCart } from '@/api/order/cart'
import { prepareOrder } from '@/api/order/order'
import { copyOf } from '@/common/utils/ObjectUtil'

@Component
export default class GoodsDetail extends Vue {
  @Getter('userId') private userId: any
  private spu: any = {}
  private skuList: any = []
  private checkList: any = {}  // 选中的属性
  private checkSku: any = {
    id: '',
    image: '',
    price: '',  // 显示的价格
    residue: 0, // 剩余库存个数
    num: 1      // 选择的个数
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
    const cartItem = {
      skuId: this.checkSku.id,
      userId: this.userId,
      name: this.spu.title,
      image: this.checkSku.image,
      originalPrice: this.checkSku.price,  // 原价
      num: this.checkSku.num
    }
    addCart(cartItem).then((res: any) => {
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
    // 获取选中的skuId
    const orderItem: any = []
    orderItem.push({ skuId: this.checkSku.id, num: this.checkSku.num })
    prepareOrder({ userId: this.userId, orderItem }).then((res: any) => {
      this.$router.push(`/order/buy/${res.data}`)
    })
  }
   
  // 初始化商品信息
  private initGoods() {
    getGoods(this.$route.params.id).then((res: any) => {
      this.$log.info('获取商品', res)
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
      this.checkList = copyOf(this.checkList)
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
.sku-amount {
  padding-bottom: 10px;
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
  