<template>
  <el-dropdown>
    <div class="el-dropdown-link" style="">
      <router-link :to="'/center/'+userId" class="nav-router-link">
        订单中心
      </router-link>
    </div>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item>
        查看更多
      </el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { Getter } from 'vuex-class'

@Component
export default class UploadDrawer extends Vue {
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
