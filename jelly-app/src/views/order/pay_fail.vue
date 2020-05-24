<template>
  <div class="container">
    <!-- 步骤条 -->
    <el-steps :space="150" :active="4" finish-status="success" class="pt-3 pb-2">
      <el-step title="购物车"></el-step>
      <el-step title="确认订单"></el-step>
      <el-step title="支付" status="error"></el-step>
      <el-step title="未完成" status="error"></el-step>
    </el-steps>
    
    <div class="text-center border p-5">
      <p>订单超时取消</p>
      <div>
        <el-button type="primary">查看订单</el-button>
        <el-button>继续购物</el-button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class  extends Vue {
}
</script>

<style scoped lang="scss">

</style>
