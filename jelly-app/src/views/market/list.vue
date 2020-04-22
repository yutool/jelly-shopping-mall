<template>
  <div>
    <div class="banner-wrapper">
      <div class="slideshow">
        <el-carousel trigger="click" height="150px">
          <el-carousel-item v-for="item in 4" :key="item">
            <h3 class="small">{{ item }}</h3>
          </el-carousel-item>
        </el-carousel>
      </div>
      <div class="catalog">
        Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ut minus consectetur quas, molestias animi eos nesciunt libero id necessitatibus sunt a quibusdam ad sit molestiae. Harum odio magni aut tenetur.
      </div>
    </div>
    <div class="container">
      <el-row :gutter="20">
        <el-col :md="6" v-for="i in 4" :key="i">
          <goods-card />
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import GoodsCard from './components/GoodsCard.vue'

@Component({
  components: {
    GoodsCard
  }
})
export default class GoodsList extends Vue {
}
</script>

<style scoped lang="scss">
.banner-wrapper {
  position: relative;
}
.catalog {
  position: absolute;
  top: 0;
  left: 0;
  z-index: 99;
  width: 400px;
  margin-left: 100px;
}
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  height: 100px;
  line-height: 150px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>