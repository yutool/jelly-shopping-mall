<template>
  <div>
    <div class="banner-wrap mb-3">
      <el-carousel trigger="click" height="450px">
        <el-carousel-item>
          <img src="@/assets/1.jpg" alt="">
        </el-carousel-item>
        <el-carousel-item>
          <img src="@/assets/2.jpg" alt="">
        </el-carousel-item>
      </el-carousel>
    </div>
    <div class="container">
      <el-row :gutter="20">
        <el-col :md="16">
          <el-card class="box-card">
            购物商城
            <div v-for="o in 4" :key="o" class="text item">
              {{'列表内容 ' + o }}
            </div>
          </el-card>
        </el-col>
        <el-col :md="8">
          <el-card class="box-card">
            限时快抢
            <div v-for="o in 4" :key="o" class="text item">
              {{'列表内容 ' + o }}
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class Home extends Vue {
  
}
</script>

<style scoped lang="scss">
.banner-wrap {
  img {
    width: 100%;
    overflow: hidden;
  }
}
</style>
