<template>
  <div style="height: 36px;">
    <el-dropdown class="pl-3" @command="handleCommand" @click.native="$router.push(`/user/${user.id}`)">
      <el-avatar class="user-avatar" :size="36">
        <img v-if="user" :src="user.avatar"/>
        <span v-else>游客</span>
      </el-avatar>
      <el-dropdown-menu slot="dropdown" class="user-dropdown">
        <div v-if="JSON.stringify(user) !== '{}'" class="text-center">
          <span>{{user.username}}</span>
          <el-dropdown-item :command="'user/' + user.id">个人空间</el-dropdown-item>
          <el-dropdown-item @click.native="logout">退出</el-dropdown-item>
        </div>
        <div v-else>
          <el-dropdown-item @click.native="openLoginDialog">登录/注册</el-dropdown-item>
        </div>
      </el-dropdown-menu>
    </el-dropdown>
    <login-dialog />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { Action, State } from 'vuex-class';
import LoginDialog from './components/LoginDialog.vue';

@Component({
  components: {
    LoginDialog,
  },
})
export default class Avatar extends Vue {
  @State((state: any) => state.account.user) private user: any
  @Action('app/openLoginDialog') private openLoginDialog: any
  
  // data
  private dialogVisible = false;
  // method
  private handleCommand(command: string) {
    if (command === undefined) {
      return ;
    }
    this.$message('click on item ' + command);
    this.$router.push(`/${command}`);
  }
  private logout() {
    this.$store.dispatch('account/logout');
  }
}
</script>

<style scoped lang="scss">
.user-avatar {
  cursor: pointer;
}
</style>
