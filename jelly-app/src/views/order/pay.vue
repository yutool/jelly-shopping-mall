<template>
  <div class="container">
    <el-steps :space="200" :active="3" finish-status="success">
      <el-step title="购物车"></el-step>
      <el-step title="确认订单"></el-step>
      <el-step title="支付"></el-step>
      <el-step title="完成"></el-step>
    </el-steps>
    
    <div>
      <p>订单提交成功，请您及时付款，以便尽快为您发货~</p>
      <small>请您在半小时之内完成支付，超级订单会自动取消，订单号:{{ order.id }}</small>
    </div>
    
    <div>
      <h6>重要说明</h6>
      <ul>
        <li>果冻商城支付平台目前支持微信支付方式。</li>
        <li>它支付渠道正在调试中，敬请期待。</li>
      </ul>
    </div>
    <div v-if="wxCodeUrl != ''">
      <vue-qrious :value="wxCodeUrl" :padding="10" :size="200" />
    </div>
    <el-button @click="weixinPay">点击获取微信二维码</el-button>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { getOrder } from '@/api/order'
import { weixinPay } from '@/api/pay'
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
  
  // 申请微信支付
  private weixinPay() {
    weixinPay(this.order).then((res: any) => {
      this.$log.info('申请支付', res)
      this.wxCodeUrl = res.data.code_url
    })
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

</style>
