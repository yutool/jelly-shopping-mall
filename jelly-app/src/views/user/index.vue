<template>
  <div class="m-theme" v-loading.fullscreen="loading">
    <div class="container">
      <!-- 用户导航栏 -->
      <div class="user-nav m-content">
        <!-- 简介 -->
        <div class="info-wrap">
          <div class="user-info clearfix">
            <div class="float-left pl-5">
              <el-avatar :size="70"
                src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png">
              </el-avatar>
            </div>
            <div class="float-left pl-4">
              <div class="username"> {{user.username}} </div>
              <div>null</div>
            </div>
          </div>
        </div>
        <!-- 导航栏 -->
        <div class="nav-wrap">
          <ul>
            <li>
              <router-link to="info">个人资料</router-link>
            </li>
            <li>
              <router-link to="address">收货地址</router-link>
            </li>
            <li>
              <router-link to="security">安全设置</router-link>
            </li>
          </ul>
        </div>
      </div>
      <!-- 内容-->
      <router-view />
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { State } from 'vuex-class'

@Component
export default class User extends Vue {
  @State((state: any) => state.account.user) private user: any
  @State((state: any) => state.app.loading) private loading: any
}
</script>

<style scoped lang="scss">
.user-nav {
  border-bottom-left-radius: 5px;
  border-bottom-right-radius: 5px;
  .info-wrap {
    padding-top: 100px;
    background: url("../../assets/h-user.jpg");
    .user-info {
      background: linear-gradient(rgba(0, 0, 0, 0), rgba(0, 0, 0, .3));
      .username {
        color: #fff;
        font-size: 1.5rem;
      }
    }
  }
  .nav-wrap {
    ul {
      padding: 0;
    }
    li {
      display: inline-block;
      list-style: none;
      padding: 5px 10px;
    }
  }
}
</style>
