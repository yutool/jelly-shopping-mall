<template>
  <div class="goodsCard" @click="goodsDetail(1)">
    <div class="goods-img">
      <img src="http://img12.360buyimg.com/n1/s450x450_jfs/t1/118864/39/1189/123780/5e958137E61771fd7/4530fe910bd14776.jpg" alt="">
    </div>
    <div>{{ goods.title }}</div>
    <el-row>
      <el-col :span="15">
        <div>
          <span>￥{{ goods.price }}</span>
          <s> {{goods.costPrice}} </s>
        </div>
        <div>
          <span>已售：{{ goods.num-goods.stockCount }}</span>
          <el-progress :percentage="(goods.num-goods.stockCount) / goods.num * 100">
          </el-progress>
        </div>
      </el-col>
      <el-col :span="9">
        <el-button size="small" @click="goodsDetail(goods.spuId)">
          立即抢购
        </el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator'

@Component
export default class GoodsCard extends Vue {
  @Prop() private goods: any
  
  private goodsDetail(id: string) {
    this.$router.push(`/market/detail/id`)
  }
}
</script>

<style scoped lang="scss">
.goodsCard {
  cursor: pointer;
  .goods-img {
    text-align: center;
    height: 200px;
    img {
      height: 100%;
    }
  }
  padding: 10px;
  border-radius: 5px;
  margin-bottom: 20px;
  box-shadow: 1px 1px 1px 1px #666;
}
</style>
