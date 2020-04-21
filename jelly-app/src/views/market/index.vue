<template>
  <div>
    <div class="container">
      Lorem ipsum dolor sit amet consectetur, adipisicing elit. Aperiam placeat autem, voluptatibus amet illum quam consequuntur ea possimus rerum laudantium, ab quod tempora dignissimos. Incidunt adipisci nulla aspernatur ipsum inventore?
    </div>
    <el-row>
      <el-button>默认按钮</el-button>
      <el-button type="primary">主要按钮</el-button>
      <el-button type="success">成功按钮</el-button>
      <el-button type="info">信息按钮</el-button>
      <el-button type="warning">警告按钮</el-button>
      <el-button type="danger">危险按钮</el-button>
    </el-row>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class Market extends Vue {
}
</script>

<style scoped lang="scss">

</style>
