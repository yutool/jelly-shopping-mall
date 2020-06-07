<template>
  <div style="margin-bottom: 56px">
    <nav class="navbar navbar-expand-lg fixed-top navbar-light bg-white shadow-sm">
      <div class="container">
        <!-- logo -->
        <router-link to="/" class="navbar-brand p-0">
          <img src="@/assets/logo.png" width="90px" />
        </router-link>
        <!-- 导航菜单 -->
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <NavMenu />
        </div>
        <!-- 其他组件 -->
        <SearchBox class="hidden-sm-and-down"/>
        <Cart />
        <Order />
        <MessageDrawer />
        <Avatar />
        <!-- 导航菜单按钮 -->
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <i class="el-icon-s-operation"></i>
        </button>
      </div>
    </nav>
  </div>
  
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import NavMenu from './NavMenu.vue';
import MessageDrawer from './MessageDrawer.vue';
import SearchBox from './SearchBox.vue';
import Cart from './Cart.vue';
import Order from './Order.vue';
import Avatar from './Avatar.vue';

@Component({
  components: {
    NavMenu, SearchBox, Order, Cart, MessageDrawer, Avatar,
  },
})
export default class NavBar extends Vue {
}
</script>

<style scoped lang="scss">
.container {
  padding: 0 10px;
}

</style>
