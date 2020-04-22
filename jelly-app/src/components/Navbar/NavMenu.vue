<template>
  <el-menu :default-active="activeIndex" class="border-0 mr-auto" mode="horizontal" @select="handleSelect" router>
    <el-menu-item index="1" route="/market">主商城</el-menu-item>
    <el-menu-item index="2" route="/seckill">限时快抢</el-menu-item>
    <el-menu-item index="3" route="/quality">精品推荐</el-menu-item>
    <el-menu-item index="4" route="/brand">品牌抢购</el-menu-item>
    <el-menu-item index="5" route="/live">直播</el-menu-item>
  </el-menu>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class NavMenu extends Vue {
  private activeIndex = '0';
  public handleSelect(index: string) {
    this.activeIndex = index;
  }
}
</script>

<style scoped lang="scss">

</style>
