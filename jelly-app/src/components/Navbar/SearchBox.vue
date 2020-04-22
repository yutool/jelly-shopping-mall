<template>
  <div class="pr-3">
    <el-input
      placeholder="搜索商品"
      prefix-icon="el-icon-search"
      size="small"
      clearable
      v-model="searchText">
    </el-input>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class SearchBox extends Vue {
  private searchText = '';
}
</script>

<style scoped lang="scss">

</style>
