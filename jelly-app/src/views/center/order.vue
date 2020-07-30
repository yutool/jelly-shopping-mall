<template>
  <div class="mb-5">
    <!-- 筛选 -->
    <div class="mb-3 mt-3">
      <el-badge :value="orderList.length" class="mr-4">
        <el-button size="medium" plain>全部订单</el-button>
      </el-badge>
      <el-badge :value="orderList.filter(i => i.status <= 1).length" class="mr-4">
        <el-button size="medium" plain>已完成</el-button>
      </el-badge>
      <el-badge :value="orderList.filter(i => i.status == 2).length" class="mr-4">
        <el-button size="medium" plain>待付款</el-button>
      </el-badge>
      <el-badge :value="orderList.filter(i => i.status == 4).length" class="mr-4">
        <el-button size="medium" plain>待发货</el-button>
      </el-badge>
      <el-badge :value="orderList.filter(i => i.status == 5).length" class="mr-4">
        <el-button size="medium" plain>待收货</el-button>
      </el-badge>
      <el-badge :value="orderList.filter(i => i.status == 6).length" class="mr-4">
        <el-button size="medium" plain>待评价</el-button>
      </el-badge>
    </div>

    <!-- 订单列表 -->
    <div v-if="orderList.length" class="table-responsive-lg">
      <table class="table order-table">
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
            <th scope="col">数量</th>
            <th scope="col">操作</th>
            <th scope="col">实付款</th>
            <th scope="col">状态</th>
            <th scope="col">操作</th>
          </tr>
        </thead>
        <!-- 订单数据 -->
        <tbody v-for="order in orderList" :key="order.id" >
          <div style="height: 10px"></div>
          <tr class="bg-light">
            <td colspan="8">
              <span>{{ order.createTime }} 订单编号：{{ order.id }}</span>
              <span class="float-right">
                <el-popconfirm v-if="order.status==0" 
                    title="确认删除订单吗" @onConfirm="deleteOrder(order.id)">
                  <i class="el-icon-delete pointer" slot="reference"></i>
                </el-popconfirm>
              </span>
            </td>
          </tr>
          <tr v-for="(item, index) in order.orderItem" :key="item.skuId">
            <td colspan="2">
              <el-col :span="9">
                <img :src="item.image" alt="" width="70">
              </el-col>
              <el-col :span="15">
                <router-link :to="'/market/detail/' + item.spuId">
                  {{ item.name }}
                </router-link>
                <div class="item-sku" v-for="(val, key) in JSON.parse(item.sku)" :key="key">
                  {{ key }}：{{ val }}
                </div>
              </el-col>
            </td>
            <td> 
              <div>￥{{ item.price }}</div>
              <div class="text-black-50"> <s>￥{{ item.originalPrice }}</s> </div>
            </td>
            <td>
              {{ item.num }}
            </td>
            <td v-if="index == 0" :rowspan="item.length">
              <a href="#">退款 / 换货</a> <br />
              <a href="#">投诉卖家</a>
            </td>
            <td v-if="index == 0" :rowspan="item.length">
              <span class="order-price">￥{{ order.payMoney }}</span>
            </td>
            <td v-if="index == 0" :rowspan="item.length">
              <div>
                <span v-if="order.status==2">待付款</span>
                <span v-if="order.status==4">待发货</span>
                <span v-if="order.status==0">交易关闭</span>
              </div>
              <div><a href="#">订单详情</a></div>
              <div><a href="#">查看物流</a></div>
            </td>
            <td v-if="index == 0" :rowspan="item.length">
              <el-button v-if="order.status==2" type="danger" size="mini" @click="$router.push(`/order/pay/${order.id}`)">去付款</el-button>
              <el-button v-if="order.status==4" type="primary" size="mini">提醒发货</el-button>
              <el-button v-if="order.status==5" type="primary" size="mini">确认收货</el-button>
              <el-button v-if="order.status==0" type="info" size="mini" @click="$router.push(`/market/detail/${item.spuId}`)">再来一单</el-button>
            </td>
          </tr>
        </tbody>
      </table>
      <!-- 分页 -->
      <el-pagination
        class="text-center"
        @size-change="handleSizeChange"
        @current-change="getUserOrder"
        :current-page="pageInfo.pageNum"
        :page-sizes="[10, 20, 30, 50]"
        :page-size="pageInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pageInfo.total">
      </el-pagination>
    </div>
    
    <!-- 无订单显示 -->
    <div v-else class="text-center pt-5">
      你还没有购买过商品哦~~~
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
  private pageInfo: any = { size: 10 }  // 分页信息
  private value = '0'
  private orderList: any = []
  
  // 获取用户的订单
  private getUserOrder(page: number, size = this.pageInfo.pageSize) {
    const userId = this.$route.params.id
    getUserOrder(userId, page, size).then((res: any) => {
      this.$log.info('获取用户订单', res)
      const {data} = res
      this.pageInfo = data
      this.orderList = data.list
    })
  }
  
  // 切换显示条数
  private handleSizeChange(size: number) {
    this.getUserOrder(this.pageInfo.pageNum, size)
  }
  
  // 删除订单
  private deleteOrder(id: string) {
    deleteOrder(id).then((res: any) => {
      this.$log.info('删除订单：', res)
      this.orderList = this.orderList.filter((item: any) =>  item.id !== id)
    })
  }
  
  private mounted() {
    this.getUserOrder(0, this.pageInfo.size)
  }
}
</script>

<style scoped lang="scss">
.table-responsive-lg {
  th {
    min-width: 90px;
  }
}
.order-table {
  font-size: 14px;
  color: #666;
  a {
    color: #666;
    text-decoration: none;
  }
  a:hover { color: #ff5777; }
  .order-price {
    color: #ff5777;
    font-size: 16px;
  }
  .item-sku {
    font-size: 12px;
    color: #999;
  }
}

</style>