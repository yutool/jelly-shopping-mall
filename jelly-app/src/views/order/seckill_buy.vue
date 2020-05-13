<template>
  <div class="container">
    <el-steps :space="200" :active="2" finish-status="success">
      <el-step title="购物车"></el-step>
      <el-step title="确认订单"></el-step>
      <el-step title="支付"></el-step>
      <el-step title="完成"></el-step>
    </el-steps>
    
    <el-button type="primary" @click="addAddress">添加收货地址</el-button>
    
    <div class="table-responsive-lg">
      <div class="bg-light">
        <table class="table table-borderless">
          <tbody>
            <tr v-for="item in order.orderItem" :key="item.id">
              <td> 
                <img :src="item.image" width="30px"> 
                {{ item.name }}
                {{ item.sku }}
              </td>
              <td>
                {{ item.price }}
              </td>
              <td>
                x {{ item.num }}
              </td>
              <td>
                优惠价多少
              </td>
              <td>
                {{ item.price }} - 优惠价
              </td>
            </tr>
          </tbody>
        </table>
        <div>
          <span>备注:</span>
          <input type="text">
        </div>
        <div>
          合计：xxx
        </div>
      </div>
      
    </div>
    
    <div class="border clearfix">
      <a href="#" class="float-left">返回购物车</a>
      <div v-if="JSON.stringify(order) !== '{}'" class="float-right">
        <span>共有{{ order.orderItem.length }}件商品</span>
        <span>共计￥{{ order.money }}</span>
        <el-button type="primary" @click="pay">确认并付款</el-button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { copyObj } from '@/common/utils/ObjectUtil'
import { createOrder, getOrder } from '@/api/order'

@Component
export default class Buy extends Vue {
  private order: any = {}
  
  private addAddress() {
    this.order.addressId = 1
  }
  
  // 创建订单，在这之前需要添加收货地址
  private pay() {
    this.$router.push(`/order/pay/${this.order.id}`)
  }
  
  // 查询订单
  private getOrder() {
    getOrder(this.$route.params.id).then((res: any) => {
      this.order = res.data
    })
  }
  
  private mounted() {
    this.getOrder()
    this.$log.info('订单', this.order)
  }
}
</script>

<style scoped lang="scss">

</style>
