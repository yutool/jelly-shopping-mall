<template>
  <ul class="navbar-nav">
    <li class="nav-item">
      <router-link class="nav-link" to="/market">主商城</router-link>
    </li>
    <li class="nav-item">
      <router-link class="nav-link" to="/seckill">限时快抢</router-link>
    </li>
    <li class="nav-item">
      <router-link class="nav-link" to="/quality">精品推荐</router-link>
    </li>
    <li class="nav-item">
      <router-link class="nav-link" to="/brand">品牌抢购</router-link>
    </li>
    <li class="nav-item">
      <router-link class="nav-link" to="/live">直播</router-link>
    </li>
  </ul>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'

@Component
export default class NavMenu extends Vue {
}
</script>

<style scoped lang="scss">
.nav-item {
  color: #909399;
  padding-right: 5px;
  padding-left: 5px;
}
</style>
