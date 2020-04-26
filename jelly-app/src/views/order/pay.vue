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
      <small>请您在半小时之内完成支付，超级订单会自动取消，订单号:{{orderId}}</small>
    </div>
    
    <div>
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
// import QRCode from 'qrcodejs2'

@Component
export default class Pay extends Vue {
  private orderId!: string
  
  // private qrcode() {
  //   let qrcode = new QRCode('qrcode', {
  //     width: 200, // 设置宽度，单位像素
  //     height: 200, // 设置高度，单位像素
  //     text: 'https://www.baidu.com'   // 设置二维码内容或跳转地址
  //   })
  // }
  
  private mounted() {
    this.orderId = this.$route.params.id
    console.log(this.orderId)
  }
}
</script>

<style scoped lang="scss">

</style>
