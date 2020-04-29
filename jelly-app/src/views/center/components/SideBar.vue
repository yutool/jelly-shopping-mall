<template>
  <div>
    <router-link to="/center/info/1">
      <el-avatar icon="el-icon-user-solid"></el-avatar>
      username
    </router-link>
    <ul>
      <li>
        <div>订单中心</div>
        <ul>
          <li> <router-link to="/center/order/1">我的订单</router-link> </li>
          <li>团购订单</li>
          <li>退款退货</li>
          <li>订单回收站</li>
        </ul>
      </li>
      <li>
        <div>关注中心</div>
        <ul>
          <li>关注的店铺</li>
          <li>关注的商品</li>
          <li>关注的品牌</li>
          <li>浏览记录</li>
        </ul>
      </li>
      <li>
        <div>客户服务</div>
        <ul>
          <li>返修退换货</li>
          <li>价格保护</li>
          <li>交易纠纷</li>
          <li>我的发票</li>
        </ul>
      </li>
    </ul>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'

@Component
export default class SideBar extends Vue {
}
</script>

<style scoped lang="scss">

</style>