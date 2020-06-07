<template>
  <router-link :to="'detail/'+spu.id" class="goods-card p-3">
    <div class="goods-image">
      <img :src="spu.picture" alt="">
    </div>
    <div>
      <div class="goods-title-spu">
        <div class="text-over">{{ spu.name }}</div>
        <div class="spu-title text-over">{{ spu.title }}</div> 
      </div>
      <div class="goods-info">
        <span class="goods-price">￥{{ spu.price }}</span>
        <span class="float-right">已售：{{ spu.saleNum }}</span>
      </div>
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
.goods-card {
  display: block;
  border: 1px solid #e5e5e5;
  color: #000;
  text-decoration: none;
  margin-bottom: 1rem;
  .spu-title {
    color: #666;
    font-size: 14px;
  }
  .goods-info {
    position: relative;
    border-top: 1px dashed #e5e5e5;
    margin-top: 12px;
    padding-top: 12px;
    color: #666;
    .goods-price {
      color: #FF4466;
    }
  }
  .goods-image {
    img {
      max-width: 100%;
      height: 200px;
    }
  }
}

</style>
