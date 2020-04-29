<template>
  <div class="container">
    <div>
      <el-button>全部订单</el-button>
      <el-button>代付款</el-button>
      <el-button>待收货</el-button>
      <el-button>待评价</el-button>
    </div>

    <div class="table-responsive-lg">
      <table class="table">
        <thead>
          <tr>
            <th scope="col">
              <el-select v-model="value" size="mini" >
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
        
        <tbody v-for="i in 3" :key="i" >
          <div style="height: 30px"></div>
          <tr class="bg-light">
            <td colspan="7">
              <span>2017-2-11 订单编号：11</span>
              <span class="float-right">
                <el-popconfirm title="确认删除订单吗" >
                  <i class="el-icon-delete" slot="reference"></i>
                </el-popconfirm>
              </span>
            </td>
          </tr>
          <tr>
            <td colspan="2">图片，名称</td>
            <td>100</td>
            <td rowspan="2">200</td>
            <td rowspan="2">张三</td>
            <td rowspan="2">已完成</td>
            <td rowspan="2">评论</td>
          </tr>
          <tr>
            <td colspan="2">图片，名称</td>
            <td>100</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'

@Component
export default class List extends Vue {
  private options = [{
          value: '0',
          label: '全部订单'
        }, {
          value: '1',
          label: '近半年订单'
        }, {
          value: '2',
          label: '近一年订单'
        }]
  private value = '0'
}
</script>

<style scoped lang="scss">
.table-responsive-lg {
  th {
    min-width: 100px;
  }
}
</style>