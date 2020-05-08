<template>
  <div class="container">
    <el-page-header @back="goBack" content="添加秒杀商品">
    </el-page-header>
    <div class="seckill-form">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="商品标题">
          <el-input v-model="form.title" disabled></el-input>
        </el-form-item>
        <el-form-item label="活动时间">
          <el-date-picker type="date" placeholder="选择日期" v-model="form.startTime"></el-date-picker>
          <span class="pl-2 pr-2"> - </span>
          <el-date-picker type="date" placeholder="选择日期" v-model="form.endTime"></el-date-picker>
        </el-form-item>
        <el-form-item label="时间段">
          <el-select v-model="form.region" placeholder="选择秒杀时间段">
            <el-option v-for="i in 6" :key="i" :value="(i-1) * 2 "
            :label="(i-1) * 2 + ' - ' + i * 2 + ' 时间段'" >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="秒杀价格">
          <el-input v-model="form.costPrice" style="width: 90px"></el-input>
          <span class="pl-3 pr-3">原价格：</span>
          <el-input v-model="form.price" style="width: 90px" disabled></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary">立即添加</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class AddSeckill extends Vue {
  private form = {
    skuId: '',
    title: '',
    price: '',
    costPrice: '',
    startTime: '',
    endTime: '',
    region: ''
  }
  private goBack() {
    this.$router.back()
  }
  private getGoods() {
    // ...
  }
  private mounted() {
    this.getGoods()
  }
}

</script>

<style scoped lang="scss">
.seckill-form {
  padding: 20px;
}
</style>
