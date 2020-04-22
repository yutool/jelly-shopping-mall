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
    <el-table :data="tableData" class="w-100 mb-3">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="商品名称">
              <span>{{ props.row.name }}</span>
            </el-form-item>
            <el-form-item label="所属店铺">
              <span>{{ props.row.shop }}</span>
            </el-form-item>
            <el-form-item label="商品 ID">
              <span>{{ props.row.id }}</span>
            </el-form-item>
            <el-form-item label="店铺 ID">
              <span>{{ props.row.shopId }}</span>
            </el-form-item>
            <el-form-item label="商品分类">
              <span>{{ props.row.category }}</span>
            </el-form-item>
            <el-form-item label="店铺地址">
              <span>{{ props.row.address }}</span>
            </el-form-item>
            <el-form-item label="商品描述">
              <span>{{ props.row.desc }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
        label="商品 ID"
        prop="id">
      </el-table-column>
      <el-table-column
        label="商品名称"
        prop="name">
      </el-table-column>
      <el-table-column
        label="描述"
        prop="desc">
      </el-table-column>
    </el-table>
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
export default class GoodsList extends Vue {
  private tableData = [{
          id: '12987122',
          name: '好滋好味鸡蛋仔',
          category: '江浙小吃、小吃零食',
          desc: '荷兰优质淡奶，奶香浓而不腻',
          address: '上海市普陀区真北路',
          shop: '王小虎夫妻店',
          shopId: '10333'
        }, {
          id: '12987123',
          name: '好滋好味鸡蛋仔',
          category: '江浙小吃、小吃零食',
          desc: '荷兰优质淡奶，奶香浓而不腻',
          address: '上海市普陀区真北路',
          shop: '王小虎夫妻店',
          shopId: '10333'
        }, {
          id: '12987125',
          name: '好滋好味鸡蛋仔',
          category: '江浙小吃、小吃零食',
          desc: '荷兰优质淡奶，奶香浓而不腻',
          address: '上海市普陀区真北路',
          shop: '王小虎夫妻店',
          shopId: '10333'
        }, {
          id: '12987126',
          name: '好滋好味鸡蛋仔',
          category: '江浙小吃、小吃零食',
          desc: '荷兰优质淡奶，奶香浓而不腻',
          address: '上海市普陀区真北路',
          shop: '王小虎夫妻店',
          shopId: '10333'
        }]
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
