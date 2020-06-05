<template>
  <el-dropdown>
    <div class="el-dropdown-link" style="">
      <router-link :to="'/order/cart/'+userId" class="nav-router-link">
        购物车
      </router-link>
    </div>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item>查看更多</el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { Getter } from 'vuex-class'

@Component
export default class History extends Vue {
  @Getter('userId') private userId!: string
}
</script>

<style scoped lang="scss">
.el-dropdown-link {
  font-size: 1rem;
  padding: 0 1rem;
  .nav-router-link {
    color: #909399;
    text-decoration: none;
  }
}
</style>
