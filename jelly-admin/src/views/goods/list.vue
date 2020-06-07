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
      <table class="table text-center">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">商品编号</th>
            <th scope="col">封面</th>
            <th scope="col">商品名称</th>
            <th scope="col">商品详情</th>
            <th scope="col">商品描述</th>
            <th scope="col">SKU库存</th>
            <th scope="col">销量</th>
            <th scope="col">审核状态</th>
            <th scope="col">操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in spuList" :key="index">
            <th scope="row">{{ index+1 }}</th>
            <td>{{ item.id }}</td>
            <td><img :src="item.picture" alt="" width="20"></td>
            <td>{{ item.name }}</td>
            <td><a href="#"><i class="el-icon-edit-outline"></i></a></td>
            <td><a href="#"><i class="el-icon-edit-outline"></i></a></td>
            <td><a href="#"><i class="el-icon-edit-outline"></i></a></td>
            <td>{{ item.saleNum }}</td>
            <td>{{ spuStatus[item.status] }}</td>
            <td>
              <a href="#">查看</a><a href="#">编辑</a> <br/>
              <router-link :to="'/seckill/add/'+item.id">秒杀</router-link>
              <a href="#">删除</a>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <!-- 分页 -->
    <div class="block text-center">
      <el-pagination
        @current-change="skipPage"
        @size-change="handleSizeChange"
        :current-page="pageInfo.pageNum"
        :page-sizes="[10, 30, 50]"
        :page-size="pageInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pageInfo.total">
      </el-pagination>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { getSpuList } from '@/api/spu';

@Component
export default class GoodsList extends Vue {
  private spuStatus = ['审核通过', '审核中', '审核失败']
  private pageInfo: any = { size: 10 }  // 初始化页面大小
  private spuList: any = {}
  
  // test -------------------
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
  // -------------------test
  
  // 切换页面
  private skipPage(page: any, size = this.pageInfo.pageSize) {
    getSpuList(page, size).then((res: any) => {
      const { data } = res
      this.pageInfo = data
      this.spuList = data.list
    })
  }
  // 切换显示条数
  private handleSizeChange(size: any) {
    this.skipPage(this.pageInfo.pageNum, size)
  }
  // 添加秒杀商品
  private seckill(goods: any) {
    this.$router.push({ name: 'addSeckill', params: {goods} })
  }
  
  private mounted() {
    this.skipPage(0, this.pageInfo.size)
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
.table-responsive-lg {
  td {
    min-width: 100px;
  }
}
</style>
