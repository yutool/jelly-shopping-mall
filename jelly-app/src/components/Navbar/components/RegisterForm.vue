<template>
  <el-form :model="registerForm" :rules="rules" ref="registerForm" class="pl-2 pr-2">
    <el-form-item prop="username">
      <el-input v-model="registerForm.username" placeholder="请填写用户名"/>
    </el-form-item>
    <el-form-item prop="email">
      <el-input v-model="registerForm.email" placeholder="请填写邮箱"/>
    </el-form-item>
    <el-form-item prop="password">
      <el-input type="password" v-model="registerForm.password" placeholder="密码(不少于6位)"/>
    </el-form-item>
    <el-form-item prop="verifyCode" :error="verifyCodeError">
      <el-input v-model="registerForm.verifyCode" placeholder="请获取验证码">
        <el-button id="verifyBtn" slot="append" style="width: 115px;" @click="getVerifyCode">
          {{verifyHint}}
        </el-button>
      </el-input>
    </el-form-item>
    <div class="clearfix pb-2" style="font-size: 12px;">
      <span>
        注册即表示同意<a href="#">《用户协议》</a>和<a href="#">《隐私政策》</a>
      </span>
      <a href="javascript:;" class="float-right" @click="goLogin">已有账号去登陆&gt;</a>
    </div>
    <div class="login-btn pb-3">
      <el-button type="primary" @click="submitForm">注册</el-button>
      <el-button class="float-right" @click="resetForm">重置</el-button>
    </div>
  </el-form>
</template>

<script lang="ts">
import { Component, Vue, Ref, Emit } from 'vue-property-decorator'
import { Form } from 'element-ui'
import { register } from '@/api/user/user'

@Component
export default class RegisterForm extends Vue {
  @Ref('registerForm') private refRegisterForm!: Form
  // data
  private verifyHint = '获取验证码'
  private verifyCodeError = ''
  private registerForm = {
    username: '',
    email: '',
    password: '',
    verifyCode: '',
  };
  private rules = {
    username: [
      { required: true, message: '请输入用户名', trigger: 'change' },
    ],
    email: [
      { required: true, message: '请输入邮箱', trigger: 'change' },
    ],
    password: [
      { required: true, message: '请输入密码', trigger: 'change' },
    ],
    verifyCode: [
      { required: true, message: '请输入验证码', trigger: 'change' },
    ],
  };
  // method
  private submitForm() {
    this.refRegisterForm.validate((valid) => {
      if (valid) {
        console.log(this.registerForm)
        register(this.registerForm).then((res: any) => {
          this.$log.info('注册用户', res)
          this.$message({type: 'success', message: res.message})
          this.goLogin()  // 切换到登录界面
        })
      } else {
        this.$log.error('注册用户', 'error submit!!');
        return false;
      }
    })
  }
  private resetForm() {
    this.refRegisterForm.resetFields();
  }
  private getVerifyCode(e: any) { // 获取验证码
    // getRegisterEmail(this.registerForm.email).then((res: any) => {
    //   if (res.code !== this.$resCode.SUCCESS) {
    //     this.$message({type: 'error', message: res.msg});
    //   }
    // });
    // $('#verifyBtn').prop('disabled', true);
    // let second = 10;
    // const interval = setInterval(() => {
    //   second--;
    //   this.verifyHint = second + 's重新获取';
    //   if (second <= 0) {
    //     window.clearInterval(interval);
    //     this.verifyHint = '获取验证码';
    //     $('#verifyBtn').prop('disabled', false);
    //   }
    // }, 1000);
  }
  // 事件
  @Emit('switch') 
  private goLogin() {
    // ...
  }
}
</script>

<style scoped lang="scss">

</style>
