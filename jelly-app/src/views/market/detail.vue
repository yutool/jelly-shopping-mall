<template>
  <div class="container">
    <el-row :gutter="20">
      <el-col :md="12">
        <div class="goods-images">
          <img src="http://img.alicdn.com/bao/uploaded/i7/TB1ENgLE3aTBuNjSszf7eRgfpXa_034047.jpg" alt="">
        </div>
      </el-col>
      <el-col :md="12">
        <div class="goods-title-sku">
          春季新款韩版羊羔毛卫衣外套显瘦阔腿休闲裤时尚套装两件套女装
        </div>
        <div class="goods-price">
          <p>价格：99</p>
          <p>促销价：99 评价:211 累计销售：111</p>
        </div>
        <div class="goods-specs">
          <div class="specs-item" v-for="i in 5" :key="i">
            <span>网络类型</span>
            <ul class="specs-item-choose">
              <li>4G全网通</li>
              <li>4G全网通</li>
            </ul>
          </div>
        </div>
        <div class="goods-amount">
          <span>数量：</span>
          <el-input-number size="mini" v-model="num"></el-input-number>
        </div>
        <div class="goods-buy">
          <el-button>立即购买</el-button>
          <el-button>加入购物车</el-button>
        </div>
        <div class="extra-services">
          服务说明：xxx
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

@Component
export default class GoodsDetail extends Vue {
}
</script>

<style scoped lang="scss">
.goods-images {
  img {
    width: 100%;
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
  