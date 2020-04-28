<template>
  <div class="container border">
    <div class="text-center">
      <p>亲，还未支付成功哦！</p>
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
export default class  extends Vue {
}
</script>

<style scoped lang="scss">

</style>
