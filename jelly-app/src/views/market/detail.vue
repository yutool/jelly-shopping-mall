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
          <p>价格：{{ price }} 优惠价：{{ price }}</p>
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
          <el-input-number size="mini" v-model="num"></el-input-number>
          <span>剩余库存：{{ residue }}</span>
        </div>
        <div class="goods-buy">
          <el-button>立即购买</el-button>
          <el-button>加入购物车</el-button>
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
import { Component, Vue } from 'vue-property-decorator';
import { getGoods } from '@/api/spu';
import { copyObj } from '@/common/utils/ObjectUtil'

@Component
export default class GoodsDetail extends Vue {
  private spu: any = {}
  private sku: any = []
  private price = 0   // 显示的价格
  private num = 0     // 选择的个数
  private residue = 0 // 剩余库存个数
  private checkList: any = {}  // 选中的sku
  
  private initGoods() {
    getGoods(this.$route.params.id).then((res: any) => {
      const { spu, sku } = res.data
      this.spu = spu
      this.spu.skuTemplate = JSON.parse(spu.skuTemplate)
      this.sku = sku
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
  // 计算剩余库存、价格
  private calculate() {
    let num = 0
    const cKeys = Object.keys(this.checkList)
    // 计算剩余库存
    for (const item of this.sku) {
      let flag = true
      for (const c of cKeys) {
        if (item.sku.indexOf(this.checkList[c]) === -1) {
          flag = false
          break
        }
      }
      if (flag) {
        num += item.num
      }
    }
    this.residue = num
    // 计算价格
    this.price = this.spu.price
    if (cKeys.length === Object.keys(this.spu.skuTemplate).length) {
      const skuStr = Object.values(this.checkList) + ''
      for (const item of this.sku) {
        if (skuStr === item.sku) {
          this.price = item.price
          break
        }
      }
    }
    
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
  