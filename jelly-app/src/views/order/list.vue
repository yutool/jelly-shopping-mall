<template>
  <div class="container">
    <el-tabs :tab-position="'left'" class="order-el-tab-pane">
      <el-tab-pane label="全部订单">
        全部订单
      </el-tab-pane>
      <el-tab-pane label="待付款">
        待付款
      </el-tab-pane>
      <el-tab-pane label="待收货">
        待收货
      </el-tab-pane>
      <el-tab-pane label="待评价">
        待评价
      </el-tab-pane>
      <el-tab-pane label="退款退货">
        退款退货
      </el-tab-pane>
      <el-tab-pane label="订单回收站">
        订单回收站
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class OrderList extends Vue {
}
</script>

<style scoped lang="scss">

</style>
