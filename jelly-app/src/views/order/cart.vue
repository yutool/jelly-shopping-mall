<template>
  <div class="container">
    <el-steps :space="200" :active="1" finish-status="success">
      <el-step title="购物车"></el-step>
      <el-step title="确认订单"></el-step>
      <el-step title="支付"></el-step>
      <el-step title="完成"></el-step>
    </el-steps>
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
              <img :src="cart.image" width="30px" alt="图片"> 
              {{ cart.name }}
            </td>
            <td>
              <div v-for="key in Object.keys(cart.sku)" :key="key">
                <span>{{ key }}: {{ cart.sku[key] }} </span>
              </div>
            </td>
            <td>
              {{ cart.original }}
              {{ cart.price }}
            </td>
            <td>
              <el-input-number size="mini" v-model="cart.num"></el-input-number>
            </td>
            <td>{{ cart.num * cart.price }}</td>
            <td>
              <el-link type="danger">删除</el-link> <br/>
              <el-link type="primary">移入收藏夹</el-link>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="border clearfix">
      <div class="float-left">
        <el-checkbox v-model="all" @change="checkAll">全选</el-checkbox>
        <a href="#">删除选中</a>
        <a href="#">清空售馨商品</a>
        <a href="#">移入收藏夹</a>
      </div>
      <div class="float-right">
        <span>总共 {{ checkObj.num }} 件商品 </span>
        <span>共计：￥ {{ checkObj.money }} 元 </span>
        <el-button type="primary" @click="buy">去付款</el-button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { Getter } from 'vuex-class'
import { getCart } from '@/api/cart'
import { prepareOrder } from '@/api/order'

@Component
export default class Cart extends Vue {
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
  
  // 付款
  private buy() {
    // 获取选中的skuId
    const orderItem: any = []
    for (const cart of this.cartList) {
      if (this.checkObj.check[cart.id] === true) {
        orderItem.push({ skuId: cart.skuId, num: cart.num })
      }
    }
    prepareOrder({ userId: '0', orderItem }).then((res: any) => {
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
}
</style>
