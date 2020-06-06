<template>
  <div class="container">
    <!-- 步骤条 -->
    <el-steps :space="150" :active="4" finish-status="success" class="pt-3 pb-2">
      <el-step title="购物车"></el-step>
      <el-step title="确认订单"></el-step>
      <el-step title="支付"></el-step>
      <el-step title="完成"></el-step>
    </el-steps>
    
    <div class="text-center border p-5">
      <p>恭喜你，支付成功啦！</p>
      <p>支付方式：微信</p>
      <p>支付金额：xxx</p>
      <div>
        <el-button type="primary" @click="$router.push(`/center/${userId}/order`)">查看订单</el-button>
        <el-button @click="$router.push('/market/list')">继续购物</el-button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { Getter } from 'vuex-class';

@Component
export default class PaySuccess extends Vue {
  @Getter('userId') private userId!: string
}
</script>

<style scoped lang="scss">

</style>
