<template>
  <el-menu default-active="1" class="el-menu-vertical-demo" router>
    <el-submenu index="1">
      <template slot="title">
        <i class="el-icon-menu"></i>
        <span>商品管理</span>
      </template>
      <el-menu-item-group title="常规商品">
        <el-menu-item index="1-1" route="/goods/list">商品列表</el-menu-item>
        <el-menu-item index="1-2" route="/goods/add">添加商品</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="活动商品">
        <el-menu-item index="1-3" route="/goods/seckill_list">商品列表</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-menu"></i>
        <span>商品配置</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="2-1">商品分类</el-menu-item>
        <el-menu-item index="2-2">添加参数</el-menu-item>
        <el-menu-item index="2-3">图片库管理</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
    <el-menu-item index="3">
      <i class="el-icon-menu"></i>
      <span slot="title">商品审核</span>
    </el-menu-item>
    <el-menu-item index="4">
      <i class="el-icon-menu"></i>
      <span slot="title">分类管理</span>
    </el-menu-item>
    <el-menu-item index="5">
      <i class="el-icon-menu"></i>
      <span slot="title">品牌管理</span>
    </el-menu-item>
    <el-menu-item index="6">
      <i class="el-icon-menu"></i>
      <span slot="title">回收站</span>
    </el-menu-item>
  </el-menu>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class Navbar extends Vue {
}
</script>

<style scoped lang="scss">

</style>
