<template>
  <div class="container">
    <el-steps :space="200" :active="3" finish-status="success">
      <el-step title="购物车"></el-step>
      <el-step title="确认订单"></el-step>
      <el-step title="支付"></el-step>
      <el-step title="完成"></el-step>
    </el-steps>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class Pay extends Vue {
}
</script>

<style scoped lang="scss">

</style>
