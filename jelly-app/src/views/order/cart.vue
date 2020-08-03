<template>
  <div class="container mb-5" v-loading.fullscreen="loading">
    <!-- 步骤条 -->
    <el-steps :space="150" :active="1" finish-status="success" class="pt-3 pb-2">
      <el-step title="购物车"></el-step>
      <el-step title="确认订单"></el-step>
      <el-step title="支付"></el-step>
      <el-step title="完成"></el-step>
    </el-steps>
    <!-- 购物车列表-->
    <div v-if="cartList.length">
      <!-- 购物车列表 -->
      <div class="table-responsive-lg">
        <table class="table table-borderless">
          <thead class="bg-light">
            <tr>
              <th scope="col">
                <el-checkbox v-model="all" @change="checkAll">全选</el-checkbox>
              </th>
              <th scope="col">商品</th>
              <th scope="col">商品信息</th>
              <th scope="col">单价（元）</th>
              <th scope="col">数量</th>
              <th scope="col">小计（元）</th>
              <th scope="col">操作</th>
            </tr>
          </thead>
          <br />
          <tbody>
            <tr class="border" v-for="cart in cartList" :key="cart.id">
              <th scope="row">
                <el-checkbox v-model="checkObj.check[cart.id]" @change="handlerChange"></el-checkbox>
              </th>
              <td> 
                <img :src="cart.image" width="60px" alt=""> 
                {{ cart.name }}
              </td>
              <td>
                <div v-for="key in Object.keys(cart.sku)" :key="key">
                  <span class="table-sku">{{ key }}: {{ cart.sku[key] }} </span>
                </div>
              </td>
              <td>
                <div> ￥{{ cart.price }} </div>
                <div> <s class="text-muted">￥{{ cart.originalPrice }}</s>  </div>
              </td>
              <td>
                <el-input-number
                  :min="1" :max="999" :precision="0"
                  size="mini" 
                  v-model="cart.num" 
                  @change="changeNum(cart)">
                </el-input-number>
              </td>
              <td>
                <span class="table-price"> ￥{{ cart.num * cart.price }} </span>
              </td>
              <td>
                <el-link type="danger" @click="deleteCart(cart.id)">删除</el-link> <br/>
                <el-link type="primary">移入收藏夹</el-link>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <!-- 状态栏 -->
      <div class="cart-paybar clearfix">
        <div class="float-left">
          <el-checkbox v-model="all" @change="checkAll">全选</el-checkbox>
          <ul class="paybar-list">
            <li><a href="#" @click="batchDelete">删除选中</a></li>
            <li><a href="#">清空售馨商品</a></li>
            <li><a href="#">移入收藏夹</a></li>
          </ul>
        </div>
        <div class="float-right">
          总共 <span style="color: #ff5777;">{{ checkObj.num }} </span> 件商品，
          共计:<span style="color: #ff5777; font-size: 20px">￥{{ checkObj.money }}</span> 元 
          <el-button type="primary" @click="buy" size="small">去付款</el-button>
        </div>
      </div>
    </div>
    <!-- 空购物车 -->
    <div v-else class="text-center pt-5">
      购物车空空如也~~~
    </div>
    
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { Getter, State } from 'vuex-class'
import { getCart, updateNum, deleteCart, batchDelete } from '@/api/order/cart'
import { prepareOrder } from '@/api/order/order'

@Component
export default class Cart extends Vue {
  @State((state: any) => state.app.loading) private loading: any
  @Getter('userId') private userId!: string
  
  private cartList: any = []
  private all = false
  private checkObj: any = {
    check: {},   // id - 是否选中
    num: 0,     // 选中商品个数
    money: 0    // 总价格
  }
  
  // 处理按钮改变
  private handlerChange() {
    this.checkObj.num = 0
    this.checkObj.money = 0
    // 如果被选中，对于id的值为true
    for (const id of Object.keys(this.checkObj.check)) {
      if (this.checkObj.check[id] === true) {
        this.checkObj.num++;
        // 计算这件商品的总价
        for (const cart of this.cartList) {
          if (id === cart.id + '') {
            this.checkObj.money += cart.num * cart.price
            break
          }
        }
      }
    }
    // 处理全选按钮
    if (this.checkObj.num === this.cartList.length) {
      this.all = true
    } else {
      this.all = false
    }
  }
  
  // 点击全选按钮
  private checkAll() {
    for (const item of this.cartList) {
      // 当为false时点击就为true
      this.checkObj.check[item.id] = this.all
    }
    this.handlerChange()
  }

  // 修改商品数量
  private changeNum(cart: any) {
    updateNum({id: cart.id, num: cart.num}).then((res: any) => {
      this.$log.info('更新购物车数量', res)
    })
  }

  // 删除购物车商品
  private deleteCart(id: number) {
    deleteCart(id).then((res: any) => {
      for (let i = 0; i < this.cartList.length; i++) {
        if (this.cartList[i].id === id) {
          this.cartList.splice(i, 1);
          break;
        }
      }
    })
  }

  // 删除选择商品
  private batchDelete() {
    const cartIds: any = []
    for (const cart of this.cartList) {
      if (this.checkObj.check[cart.id] === true) {
        cartIds.push(cart.id)
      }
    }
    batchDelete(cartIds).then((res: any) => {
      this.getCart()
    })
  }
  
  // 付款
  private buy() {
    // 获取选中的skuId
    const orderItem: any = []
    // 添加选中商品购物车id
    const cartIds: any = []
    for (const cart of this.cartList) {
      if (this.checkObj.check[cart.id] === true) {
        orderItem.push({ skuId: cart.skuId, num: cart.num })
        cartIds.push(cart.id)
      }
    }
    
    prepareOrder({ userId: this.userId, orderItem, cartIds }).then((res: any) => {
      this.$router.push(`/order/buy/${res.data}`)
    })
  }
  
  // 获取购物车
  private getCart() {
    const userId = this.$route.params.id
    getCart(userId).then((res: any) => {
      this.cartList = res.data
      for (const cart of this.cartList) {
        cart.sku = JSON.parse(cart.sku)
      }
      this.$log.info('查询购物车', this.cartList)
    })
  }
  
  private mounted() {
    this.getCart()
  }
}
</script>

<style scoped lang="scss">
.table-responsive-lg {
  th:not(:first-child) {
    min-width: 150px;
  }
  .table-sku {
    line-height: 20px;
    color: #666;
    font-size: 13px;
  }
  .table-price {
    color: #ff5777;
    font-size: 20px;
  }
}
.cart-paybar {
  box-shadow: 0 .125rem .25rem rgba(0,0,0,.1);
  padding: 5px;
  .paybar-list {
    display: inline;
    margin: 0;
    padding: 10px;
    li {
      list-style: none;
      display: inline;
      padding-right: 5px;
    }
  }
}
</style>
