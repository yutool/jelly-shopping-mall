<template>
  <div class="container">
    <el-steps :space="200" :active="1" finish-status="success">
      <el-step title="购物车"></el-step>
      <el-step title="确认订单"></el-step>
      <el-step title="支付"></el-step>
      <el-step title="完成"></el-step>
    </el-steps>
    <el-tabs class="cart-el-tab-pane" v-model="activeName" @tab-click="handleClick">
      <!-- 全部商品 -->
      <el-tab-pane label="全部商品" name="first">
        <all-cart :cartList="cartList" />
      </el-tab-pane>
      
      <!-- 降级商品 -->
      <el-tab-pane label="降级商品" name="second">
        <dis-cart :cartList="cartList" />
      </el-tab-pane>
      
      <!-- 库存紧张 -->
      <el-tab-pane label="库存紧张" name="third">
        <less-cart :cartList="cartList" />
      </el-tab-pane>
    </el-tabs>
    
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { Getter } from 'vuex-class'
import { getCartList } from '@/api/cart'
import AllCart from './components/AllCart.vue'
import DisCart from './components/DisCart.vue'
import LessCart from './components/LessCart.vue'

@Component({
  components: {
    AllCart, DisCart, LessCart
  }
})
export default class CartList extends Vue {
  @Getter('userId') private userId!: string
  private activeName = 'first'
  private pageInfo = { size: 10 }
  private cartList = []
  
  private handleClick(tab: any, event: any) {
    // console.log(tab, event);
  }
  
  private skipPage(page: number, size = this.pageInfo.size) {
    getCartList(this.userId).then((res: any) => {
      const { data } = res
      this.pageInfo = data
      this.cartList = data.list
      this.$log.info('查询购物车', res)
    })
  }
  
  private mounted() {
    this.skipPage(0)
  }
}
</script>

<style scoped lang="scss">

</style>
