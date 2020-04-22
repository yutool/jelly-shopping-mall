<template>
  <div class="container">
    <el-tabs class="cart-el-tab-pane" v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="全部商品" name="first">
        <cart-item v-for="i in 5" :key="i" class="mb-2"/>
      </el-tab-pane>
      <el-tab-pane label="降级商品" name="second">
        <cart-item v-for="i in 3" :key="i" class="mb-2"/>
      </el-tab-pane>
      <el-tab-pane label="库存紧张" name="third">
        <cart-item v-for="i in 2" :key="i" class="mb-2"/>
      </el-tab-pane>
    </el-tabs>
    <div class="cart-pay card">
      购买
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import CartItem from './components/CartItem.vue'

@Component({
  components: {
    CartItem
  }
})
export default class CartList extends Vue {
  private activeName = 'first'
  
  private handleClick(tab: any, event: any) {
    console.log(tab, event);
  }
}
</script>

<style scoped lang="scss">

</style>
