<template>
  <router-link :to="'detail/'+spu.id" class="card text-center">
    <div class="goods-image">
      <img :src="spu.picture" alt="">
    </div>
    <div class="goods-price">
      ￥<span>{{ spu.price }}</span>
    </div>
    <div class="goods-title-spu">
      {{ spu.title }}
    </div>
    <div class="goods-status">
      <span>该月成交{{ spu.saleNum }}万笔</span>
      <span>{{ spu.commentNum }} 评论</span>
    </div>
  </router-link>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';

@Component
export default class GoodsCard extends Vue {
  @Prop() private spu: any
}
</script>

<style scoped lang="scss">
.goods-image {
  img {
    width: 100%;
  }
}
</style>
