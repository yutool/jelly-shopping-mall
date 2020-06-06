<template>
  <div>
    <div class="text-center pt-3 pb-3">
      <router-link :to="'/user/' + user.id">
        <el-avatar icon="el-icon-user-solid"></el-avatar>
        <div>{{ user.username }}</div>
      </router-link>
    </div>
    <div class="menu-wrap">
      <ul>
        <li>
          <div class="menu-title">订单中心</div>
          <ul class="menu-item">
            <li @click="$router.push(`/center/${user.id}/order`)">我的订单</li>
            <li @click="$router.push(`/center/${user.id}/team_order`)">团购订单</li>
            <li>退款退货</li>
            <li>订单回收站</li>
          </ul>
        </li>
        <li>
          <div class="menu-title">关注中心</div>
          <ul class="menu-item">
            <li>关注的店铺</li>
            <li>关注的商品</li>
            <li>关注的品牌</li>
            <li>浏览记录</li>
          </ul>
        </li>
        <li>
          <div class="menu-title">客户服务</div>
          <ul class="menu-item">
            <li>返修退换货</li>
            <li>价格保护</li>
            <li>交易纠纷</li>
            <li>我的发票</li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { Getter, State } from 'vuex-class'

@Component
export default class SideBar extends Vue {
  @State(((state: any) => state.account.user)) private user: any
}
</script>

<style scoped lang="scss">
.menu-wrap {
  ul {
    padding: 0;
    li { list-style: none; }
  }
  .menu-title {
    font-size: 1.2rem;
    background: #aaa;
  }
  .menu-item {
    li {
      padding: 3px 0;
      cursor: pointer;
    }
    li:hover { background: #ccc; }
  }
}
</style>