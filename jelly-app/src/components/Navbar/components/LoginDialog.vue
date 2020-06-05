<template>
  <el-dialog :title="dialogTitle" :visible.sync="visible" width="450px" :before-close="closeDialog" center :close-on-click-modal="false" class="m-el-dialog">
    <login-form v-if="isLogin" @switch="goRegister" />
    <register-form v-if="!isLogin" @switch="goLogin" />
  </el-dialog>
</template>

<script lang="ts">
import { Component, Vue, Ref } from 'vue-property-decorator'
import { State, Action } from 'vuex-class'
import { Form } from 'element-ui'
import LoginForm from './LoginForm.vue'
import RegisterForm from './RegisterForm.vue'

@Component({
  components: {
    LoginForm, RegisterForm,
  },
})
export default class LoginDialog extends Vue {
  @Ref('loginForm') private loginFrom!: Form
  @State((state: any) => state.app.loginDialog) private visible!: boolean
  @Action('app/closeLoginDialog') private closeDialog: any
  
  // data
  private dialogTitle = '登录'
  private isLogin = true
  // method
  private goRegister() {
    this.dialogTitle = '注册'
    this.isLogin = false
  }
  private goLogin() {
    this.dialogTitle = '登录'
    this.isLogin = true
  }
}
</script>

<style scoped lang="scss">

</style>
