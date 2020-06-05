<template>
  <div class="container">
    <!-- 筛选 -->
    <div>
      <el-button>全部订单</el-button>
      <el-button>代付款</el-button>
      <el-button>待收货</el-button>
      <el-button>待评价</el-button>
    </div>
    <!-- 订单列表 -->
    <div v-if="orderList.length" class="table-responsive-lg">
      <table class="table">
        <thead>
          <tr>
            <th scope="col">
              <el-select v-model="value" size="mini">
                <el-option v-for="item in options" :key="item.value"
                  :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </th>
            <th scope="col">订单详情</th>
            <th scope="col">单价</th>
            <th scope="col">实付</th>
            <th scope="col">收货人</th>
            <th scope="col">状态</th>
            <th scope="col">操作</th>
          </tr>
        </thead>
        
        <tbody v-for="order in orderList" :key="order.id" >
          <div style="height: 30px"></div>
          <tr class="bg-light">
            <td colspan="7">
              <span>{{ order.createTime }} 订单编号：{{ order.id }}</span>
              <span class="float-right">
                <el-popconfirm title="确认删除订单吗"  @onConfirm="deleteOrder(order.id)">
                  <i class="el-icon-delete" slot="reference"></i>
                </el-popconfirm>
              </span>
            </td>
          </tr>
          <tr v-for="(item, index) in order.orderItem" :key="item.skuId">
            <td colspan="2">
              {{ item.image }}
              {{ item.name }}
            </td>
            <td> {{ item.money }} </td>
            <td v-if="index == 0" :rowspan="item.length">{{ order.payMoney }}</td>
            <td v-if="index == 0" :rowspan="item.length">{{ order.addressId }}</td>
            <td v-if="index == 0" :rowspan="item.length">已完成</td>
            <td v-if="index == 0" :rowspan="item.length">评论</td>
          </tr>
        </tbody>
      </table>
    </div>
    <!-- 无订单显示 -->
    <div v-else class="text-center pt-5">
      你还没有下过订单哦~~~
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { getUserOrder, deleteOrder } from '@/api/order/order'

@Component
export default class List extends Vue {
  private options = [
    { value: '0', label: '全部订单' },
    { value: '1', label: '近半年订单' },
    { value: '2', label: '近一年订单' },
  ]
  private value = '0'
  private orderList: any = []
  
  // 获取用户的订单
  private getUserOrder() {
    const userId = this.$route.params.id
    getUserOrder(userId).then((res: any) => {
      this.$log.info('获取用户订单', res)
      this.orderList = res.data
    })
  }
  
  // 删除订单
  private deleteOrder(id: string) {
    deleteOrder(id).then((res: any) => {
      this.$log.info('删除订单：', res)
      this.orderList = this.orderList.filter((item: any) =>  item.id !== id)
    })
  }
  
  private mounted() {
    this.getUserOrder()
  }
}
</script>

<style scoped lang="scss">
.table-responsive-lg {
  th {
    min-width: 100px;
  }
}
</style>