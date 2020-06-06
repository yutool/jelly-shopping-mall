<template>
  <div class="container">
    <!-- 步骤条 -->
    <el-steps :space="150" :active="3" finish-status="success" class="pt-3 pb-2">
      <el-step title="购物车"></el-step>
      <el-step title="确认订单"></el-step>
      <el-step title="支付"></el-step>
      <el-step title="完成"></el-step>
    </el-steps>
    <!-- 订单信息 -->
    <div class="mb-3">
      <div>订单提交成功，请您及时付款，以便尽快为您发货~</div>
      <small>请您在半小时之内完成支付，超时订单会自动取消，订单号:{{ order.id }}</small>
    </div>
    <!-- 二维码 -->
    <div class="pay-card">
      <div class="pay-header">
        <ul>
          <li @click="weixinPay">微信支付</li>
          <li>支付宝支付</li>
        </ul>
      </div>
      <el-row class="pay-content">
        <el-col :span="9">
          <vue-qrious :value="wxCodeUrl" :padding="10" :size="200" />
        </el-col>
        <el-col :span="15">
          <div>
            <div class="pay-hint">
              <span>请使用微信扫描二维码</span> <br/>
              <span>已完成支付</span>
            </div>
            <div class="pay-helper">
              <span>详细 <a href="#">使用帮助</a></span> <br/>
              <span>如果支付遇到问题，可 <a href="#">联系客服</a></span>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <!-- 一些提示信息 -->
    <div class="hint">
      <h6>重要说明</h6>
      <ul>
        <li>果冻商城支付平台目前支持微信支付方式。</li>
        <li>它支付渠道正在调试中，敬请期待。</li>
      </ul>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { getOrder } from '@/api/order/order'
import { weixinPay } from '@/api/pay/pay'
import VueQrious from 'vue-qrious'

@Component({
  components: {
    VueQrious
  }
})
export default class Pay extends Vue {
  private payTimer: any
  private wxCodeUrl = ''
  private order = {
    id: '',
    name: '',
    money: 0
  }

  // 检查订单状态
  private checkPayStatus() {  
    this.payTimer = setInterval(() => {
      getOrder(this.$route.params.id).then((res: any) => {
        if (res.data.status === 3) {        // 3表示付款成功-待发货
          this.$log.info('订单状态：', '支付成功')
          window.clearInterval(this.payTimer)
          this.$router.push({name: 'pay_success', params: { order: res.data }})
        } else if (res.data.status === 2) {  // 2表示支付失败了
          this.$log.info('订单状态：', '支付失败')
          window.clearInterval(this.payTimer)
          this.$router.push({name: 'pay_fail', params: { order: res.data }})
        }
      })
    }, 2000)
  }
  
  // 获取订单
  private getOrder() {
    getOrder(this.$route.params.id).then((res: any) => {
      this.$log.info('pay查询订单', res)
      const { data } = res
      this.order.id = data.id
      this.order.name = '果冻订单:' + data.id
      this.order.money = data.payMoney
      // 申请微信支付
      this.weixinPay()
    })
  }
  
   // 申请微信支付
  private weixinPay() {
    weixinPay(this.order).then((res: any) => {
      this.$log.info('申请支付', res)
      this.wxCodeUrl = res.data.code_url
    })
  }
 
  private mounted() {
    this.getOrder()
    this.checkPayStatus()
    
  }
  private beforeDestroy() {
    if (this.payTimer) {
      window.clearInterval(this.payTimer)
    }
  }
}
</script>

<style scoped lang="scss">
.pay-card {
  border: 2px solid #f6f6f6;
  margin-bottom: 1rem;
  .pay-header {
    ul {
      margin: 0;
      padding: 0;
      li {
        list-style: none;
        display: inline-block;
        padding: 10px 10px;
        border: 1px solid #f6f6f6;
        cursor: pointer;
      }
    }
  }
  .pay-content {
    text-align: center;
    padding: 20px;
    background: #f9f9f9;
    .pay-hint {
      color: #999;
      font: 700 20px/1.4 Microsoft YaHei;
      padding: 30px 0;
    }
    .pay-helper {
      font-size: 14px;
      color: #666;
    }
  }
}
</style>
