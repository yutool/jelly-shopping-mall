<template>
  <router-link to="detail/1" class="card text-center">
    <div class="goods-image">
      <img src="http://img.alicdn.com/bao/uploaded/i7/TB1ENgLE3aTBuNjSszf7eRgfpXa_034047.jpg" alt="">
    </div>
    <div class="goods-price">
      ￥699.00
    </div>
    <div class="goods-title-spu">
      xiaomi、小米 Redmi
    </div>
    <div class="goods-status">
      <span>该月成交5.0万笔</span>
      <span>7000+评论</span>
    </div>
  </router-link>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class GoodsCard extends Vue {
}
</script>

<style scoped lang="scss">
.goods-image {
  img {
    width: 100%;
  }
}
</style>
