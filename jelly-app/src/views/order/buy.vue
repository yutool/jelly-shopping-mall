<template>
  <div class="container mb-5">
    <!-- 步骤条 -->
    <el-steps :space="150" :active="2" finish-status="success" class="pt-3 pb-2">
      <el-step title="购物车"></el-step>
      <el-step title="确认订单"></el-step>
      <el-step title="支付"></el-step>
      <el-step title="完成"></el-step>
    </el-steps>
    <!-- 添加收货人 -->
    <div class="pb-3">
      <div style="font-weight: 600" class="mb-2">选择收货地址</div>
      <el-row :gutter="20">
        <el-col :md="6" v-for="addr in userAddress" :key="addr.id">
          <div class="addr-card" :class="{'active': addr.isDefault}" @click="changeAddr(addr.id, $event)">
            <div class="addr-header">
              {{ addr.consignee }}
            </div>
            <div class="addr-content">
              <div>{{ addr.address }}</div>
              <div>{{ addr.detail }} {{ addr.postcode }}</div>
              <div>{{ addr.telephone }}</div>
            </div>
          </div>
        </el-col>
        <el-col :md="6">
          <el-button icon="el-icon-plus" class="add-btn" circle
            @click="goAddAddr">
          </el-button>
        </el-col>
      </el-row>
    </div>
    <!-- 商品列表 -->    
    <div class="table-responsive-lg">
      <div style="font-weight: 600" class="mb-2">确认商品信息</div>
      <div class="pb-3">
        <table class="table table-borderless">
          <thead class="bg-light">
            <th>商品</th>
            <th>商品信息</th>
            <th>单价(元)</th>
            <th>数量</th>
            <th>优惠</th>
            <th>小计(元)</th>
          </thead>
          <br />
          <tbody class="bg-light">
            <tr v-for="item in order.orderItem" :key="item.id">
              <td> 
                <img :src="item.image" width="30px" alt="图片"> 
                {{ item.name }}
              </td>
              <td>
                <div v-for="key in Object.keys(item.sku)" :key="key">
                  <span class="table-sku">{{ key }}: {{ item.sku[key] }} </span>
                </div>
              </td>
              <td>
                <span> {{ item.price }} </span>
              </td>
              <td>
                <span> x {{ item.num }} </span>
              </td>
              <td>
                无
              </td>
              <td>
                <span> {{ item.price * item.num }} </span>
              </td>
            </tr>
            <tr>
              <td colspan="6">
                <span class="text-muted">备注：</span>
                <input class="remark" placeholder="补充填写其他信息，如有快递不到也请留言">
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <!-- 确认购买 -->
    <div class="buy-paybar clearfix">
      <div class="float-left">
        <el-link style="line-height: 40px">
          <i class="el-icon-arrow-left pl-2"></i>
          返回购物车
        </el-link>
      </div>
      <div v-if="JSON.stringify(order) !== '{}'" class="float-right">
        <span class="pr-3">
          共有 <span style="color: #ff5777;">{{ order.orderItem.length }}</span> 件商品，
          共计<span style="color: #ff5777; font-size: 20px">￥{{ order.payMoney }}</span>
        </span>
        <el-button type="primary" @click="pay">确认并付款</el-button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { getPrepareOrder, checkPrepareOrder, createOrder } from '@/api/order/order'
import { Getter } from 'vuex-class'
import { getUserAddress } from '@/api/user/address'

@Component
export default class Buy extends Vue {
  @Getter('userId') private userId!: string
  
  private userAddress: any = [] // 收货地址
  private order: any = {} // 显示
  private orderModel: any = {
    id: '',
    addressId: null
  }

  // 创建订单，在这之前需要添加收货地址
  private pay() {
    if (!this.orderModel.addressId) {
      this.$message({ type: 'info', message: '请添加收货地址' })
      return
    }
    // 创建订单
    createOrder(this.orderModel).then((res: any) => {
      this.$log.info('创建订单', res)
      // 创建订单成功，申请微信支付
      this.$router.push(`/order/pay/${res.data}`)
    })
  }
  
  // 获取预订单
  private getPrepareOrder() {
    getPrepareOrder(this.$route.params.id).then((res: any) => {
      if (res.data == null) {
        this.$router.back()
      }
      this.order = res.data
      for (const item of this.order.orderItem) {
        item.sku = JSON.parse(item.sku)
      }
      this.orderModel.id = this.order.id
      this.$log.info('收到订单', this.order)
    })
  }
  
  // 获取用户收货地址
  private getUserAddress() {
    getUserAddress(this.userId).then((res: any) => {
      this.$log.info('获取用户收货地址', res)
      this.userAddress = res.data
      // 设置默认地址
      for (const addr of this.userAddress) {
        if (addr.isDefault) {
          this.orderModel.addressId = addr.id
          break
        }
      }
    })
  }
  
  // 选择收货地址
  private changeAddr(id: string, e: any) {
    // 调整类
    const $addr = $(e.currentTarget)
    $('.addr-card').removeClass('active')
    $addr.addClass('active')
    // 设置收货地址
    this.orderModel.addressId = id
  }
  
  // 前往设置地址页
  private goAddAddr() {
    const routes = this.$router.resolve(`/user/${this.userId}/address`)
    window.open(routes.href, '_blank')
  }
  
  private mounted() {
    this.getPrepareOrder()
    this.getUserAddress()
  }
  private beforeDestroy() {
    // 判断预生产的订单是否需要删除
    checkPrepareOrder(this.order.id)
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
  .remark {
    font-size: 14px;
    width: 360px;
    color: #666;
  }
}
.addr-card {
  box-shadow: 1px 1px 1px 1px #666;
  border-radius: 5px;
  padding: 10px;
  height: 160px;
  margin-bottom: 5px;
  cursor: pointer;
  .addr-header {
    padding-bottom: 5px;
    border-bottom: 1px solid #ccc;
    font-size: 1.2rem;
    font-weight: 500;
  }
  .addr-content {
    color: #666;
  }
}
.buy-paybar {
  border: 1px solid rgb(212, 212, 212);
}
.add-btn {
  margin: 50px 20px;
  font-size: 30px;
}
.active {
  background: pink
}
</style>
