<template>
  <div class="container">
    <!-- 条件筛选 -->
    <div class="goods-filtrate-btn mb-3">
      <el-button size="mini">全部商品1000</el-button>
      <el-button size="mini">已上架900</el-button>
      <el-button size="mini">未上架100</el-button>
      <el-button size="mini">待审核1</el-button>
    </div>
    <!-- 输入筛选 -->
    <div class="card mb-3">
      <div class="card-header">
        <i class="el-icon-search"></i>筛选查询
      </div>
      <div class="card-body">
        <ul class="m-list-group">
          <li>
            <label>输入搜索：</label>
            <el-autocomplete
              class="inline-input"
              size="medium"
              v-model="state2"
              :fetch-suggestions="querySearch"
              placeholder="请输入内容"
              :trigger-on-focus="false"
              @select="handleSelect"
            ></el-autocomplete>
          </li>
          <li>
            <label>商品分类：</label>
            <el-autocomplete
              class="inline-input"
              size="medium"
              v-model="state2"
              :fetch-suggestions="querySearch"
              placeholder="请输入内容"
              :trigger-on-focus="false"
              @select="handleSelect"
            ></el-autocomplete>
          </li>
          <li>
            <label>品牌选择：</label>
            <el-autocomplete
              class="inline-input"
              size="medium"
              v-model="state2"
              :fetch-suggestions="querySearch"
              placeholder="请输入内容"
              :trigger-on-focus="false"
              @select="handleSelect"
            ></el-autocomplete>
          </li>
        </ul>
      </div>
    </div>
    <!-- 商品列表 -->
    <div class="table-responsive-lg">
      <table class="table">
        <thead>
          <tr>
            <th scope="col">编号</th>
            <th scope="col">商品名称</th>
            <th scope="col">活动状态</th>
            <th scope="col">秒杀日期</th>
            <th scope="col">秒杀时间</th>
            <th scope="col">开启/关闭</th>
            <th scope="col">操作</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>Mark</td>
            <td>Mark</td>
            <td>Mark</td>
            <td>Otto</td>
            <td>@mdo</td>
            <td>@mdo</td>
            <td>
              <a href="javascript:;">编辑</a>
              <a href="javascript:;">删除</a>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <!-- 分页 -->
    <div class="text-center">
      <el-pagination :page-size="20" :pager-count="11" layout="prev, pager, next" :total="1000">
      </el-pagination>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class SeckillList extends Vue {
  private restaurants = []
  private state2 = ''
  private querySearch(queryString: any, cb: any) {
    const restaurants = this.restaurants;
    const results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
    // 调用 callback 返回建议列表的数据
    cb(results);
  }
  private createFilter(queryString: any) {
    return (restaurant: any) => {
      return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
    };
  }
  private handleSelect(item: any) {
    console.log(item);
  }
}
</script>

<style scoped lang="scss">
.m-list-group {
  padding: 0;
  margin: 0;
  li {
    display: inline-block;
    list-style: none;
    padding: 0 5px;
  }
}
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>
