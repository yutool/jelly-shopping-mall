<template>
  <div class="goodsCard" @click="goodsDetail(sku.spuId)">
    <div class="goods-img">
      <img :src="sku.image" alt="">
    </div>
    <div>{{ goods.title }}</div>
    <el-row>
      <el-col :span="15">
        <div>
          <span>￥{{ sku.price }}</span>
          <s> {{ sku.costPrice }} </s>
        </div>
        <div>
          <span>已售：{{ sku.num-sku.residue }}</span>
          <el-progress :percentage="0">
          </el-progress>
        </div>
      </el-col>
      <el-col :span="9">
        <el-button size="small" @click="goodsDetail(sku.spuId)">
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
  @Prop() private time: any
  private sku: any = {}
  
  private goodsDetail(id: string) {
    this.$router.push(`/seckill/detail/${this.time}/${id}`)
  }
  
  private mounted() {
    this.sku = this.goods.skuList[0]
    console.log(this.sku)
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
