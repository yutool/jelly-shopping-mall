<template>
  <div class="container border">
    <div class="text-center">
      <p>恭喜你，支付成功啦！</p>
      <p>支付方式：微信</p>
      <p>支付金额：xxx</p>
    </div>
    <div class="text-center">
      <el-button type="primary">查看订单</el-button>
      <el-button>继续购物</el-button>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class PaySuccess extends Vue {
}
</script>

<style scoped lang="scss">

</style>
