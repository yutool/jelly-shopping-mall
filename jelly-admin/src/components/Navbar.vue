<template>
  <nav class="navbar bg-white border-bottom p-0 fixed-top">
    <!-- logo -->
    <router-link to="/" class="navbar-brand ml-3">
      <img src="@/assets/logo.png" width="30px" />
    </router-link>
    <!-- 头像 -->
    <div class="ml-auto mr-5" style="height: 36px;">
      <el-dropdown class="pl-3" @command="handleCommand">
        <el-avatar class="user-avatar" :size="36">
          <img v-if="user" :src="user.avatar"/>
          <span v-else>游客</span>
        </el-avatar>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <div v-if="user" class="text-center">
            <span>{{user.username}}</span>
            <el-dropdown-item command="account">账号设置</el-dropdown-item>
            <el-dropdown-item command="space">个人空间</el-dropdown-item>
            <el-dropdown-item @click.native="logout">退出</el-dropdown-item>
          </div>
          <div v-else>
            <el-dropdown-item @click.native="openLoginDialog">登录/注册</el-dropdown-item>
          </div>
        </el-dropdown-menu>
      </el-dropdown>
      <login-dialog />
    </div>
  </nav>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { Action, State } from 'vuex-class';
import LoginDialog from './LoginDialog.vue'

@Component({
  components: {
    LoginDialog
  }
})
export default class Navbar extends Vue {
  @State((state: any) => state.account.user) private user: any;
  private handleCommand() {
    //
  }
  private openLoginDialog() {
    //
  }
}
</script>

<style scoped lang="scss">

</style>
