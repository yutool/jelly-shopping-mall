<template>
  <!-- 消息抽屉 -->
  <div>
    <div @click="drawer = true" class="drawer-title">
      <el-badge value="2" >
        <i class="el-icon-chat-line-square" style="font-size: 22px;"></i>
      </el-badge>
      <!-- <i class="el-icon-chat-dot-square" style="font-size: 22px;"></i> -->
    </div>
    <el-drawer title="消息中心" :visible.sync="drawer" :direction="'rtl'">
      <el-tabs v-model="messageType">
        <el-tab-pane label="用户消息" name="user">
          用户消息列表
        </el-tab-pane>
        <el-tab-pane label="系统消息" name="system">
          系统消息列表
        </el-tab-pane>
      </el-tabs>
    </el-drawer>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class MessageDrawer extends Vue {
  private drawer =  false;
  private messageType = 'user';
}
</script>

<style scoped lang="scss">
.drawer-title {
  color: #909399;
  padding: 0 1rem;
  cursor: pointer;
  height: 23px;
}
</style>
