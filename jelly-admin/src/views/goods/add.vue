<template>
  <div class="container">
    <el-steps :active="step" simple>
      <el-step title="选择商品分类" icon="el-icon-tickets"></el-step>
      <el-step title="填写商品信息" icon="el-icon-edit"></el-step>
      <el-step title="填写商品属性" icon="el-icon-sell"></el-step>
    </el-steps>
    <div>
      <!-- 步骤 1 -->
      <div v-if="step==1" class="choose-category">
        <!-- 选择分类 -->
        <div>
          <div class="step-title">
            <i class="el-icon-edit-outline"></i> 选择分类
          </div>
          <div class="category-list">
            <div class="category-item">
              <div class="category-item-title"> 一级分类 </div>
              <div class="m-list-group">
                <a href="#" class="list-group-item list-group-item-action"
                  v-for="(c, index) in category.rootItem" :key="c.id" @click="rootClick(index)" >
                  {{c.name}}
                </a>
              </div>
            </div>
            <div class="catolog-item-btn">
              <el-button icon="el-icon-arrow-right" circle disabled></el-button>
            </div>
            <div class="category-item">
              <div class="category-item-title"> 二级分类 </div>
              <div class="m-list-group">
                <a href="#" class="list-group-item list-group-item-action"
                  v-for="(c,index) in category.secondItem" :key="c.id" @click="secondClick(index)">
                  {{c.name}}
                </a>
              </div>
            </div>
            <div class="catolog-item-btn">
              <el-button icon="el-icon-arrow-right" circle disabled></el-button>
            </div>
            <div class="category-item">
              <div class="category-item-title"> 三级分类 </div>
              <div class="m-list-group">
                <a href="#" class="list-group-item list-group-item-action"
                  v-for="(c,index) in category.threeItem" :key="c.id" @click="category.threeIndex=index">
                  {{c.name}}
                </a>
              </div>
            </div>
            <div v-if="category.rootItem.length" class="cotalog-hint">
              你当前选择的商品类别是：
              <span>{{category.rootItem[category.rootIndex].name}}</span> &gt; 
              <span>{{category.secondItem[category.secondIndex].name}}</span> &gt; 
              <span>{{category.threeItem[category.threeIndex].name}}</span>
            </div>
          </div>
        </div>
        <!-- 下一步 -->
        <div class="category-next text-center">
          <el-button type="primary" @click.native="step=2">下一步，填写商品信息</el-button>
        </div>
      </div>
      <!-- 步骤 2 -->
      <div v-if="step==2" class="fill-info">
        <!-- 基本信息 -->
        <div>
          <div class="step-title">
            <i class="el-icon-edit-outline"></i> 基本信息
          </div>
          <el-form ref="form" :model="form" label-width="80px">
            <el-form-item label="商品分类">
              <a href="javascript:;" @click="reselectRoot(category.rootIndex)">{{category.rootItem[category.rootIndex].name}}</a> &gt; 
              <a href="javascript:;" @click="reselectSecond(category.secondIndex)">{{category.secondItem[category.secondIndex].name}}</a> &gt; 
              <span>{{category.threeItem[category.threeIndex].name}}</span>
            </el-form-item>
            <el-form-item label="商品名称">
              <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="商品品牌">
              <el-select v-model="form.region" placeholder="请选择活动区域">
                <el-option label="区域一" value="shanghai"></el-option>
                <el-option label="区域二" value="beijing"></el-option>
              </el-select>
            </el-form-item>
          </el-form>
        </div>
        <!-- 商品详情 -->
        <div>
          <div class="step-title">
            <i class="el-icon-edit-outline"></i> 商品详情（可编辑表格）
          </div>
          <el-form ref="form" :model="form" label-width="80px">
            <el-form-item label="商品详情">
              <el-input type="textarea" :rows="3" placeholder="请输入内容" v-model="textarea">
              </el-input>
            </el-form-item>
            <el-form-item label="服务保证">
              <el-checkbox-group v-model="form.type">
                <el-checkbox label="七天无理由" name="type"></el-checkbox>
                <el-checkbox label="快速退货" name="type"></el-checkbox>
                <el-checkbox label="免费包邮" name="type"></el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-form>
        </div>
        <!-- 下一步 -->
        <div class="category-next text-center">
          <el-button  @click.native="step=1">上一步，填写商品分类</el-button>
          <el-button type="primary" @click.native="step=3">下一步，填写商品属性</el-button>
        </div>
      </div>
      <!-- 步骤 3 -->
      <div v-if="step==3" class="fill-attribute">
        <!-- 商品属性 -->
        <div>
          <div class="step-title">
            <i class="el-icon-edit-outline"></i> 商品属性
          </div>
          <el-form ref="form" :model="form" label-width="90px">
            <el-form-item label="活动性质:">
              <el-checkbox-group v-model="form.type">
                <el-checkbox label="餐厅线上活动" name="type"></el-checkbox>
                <el-checkbox label="地推活动" name="type"></el-checkbox>
                <el-checkbox label="线下主题活动" name="type"></el-checkbox>
                <el-checkbox label="单纯品牌曝光" name="type"></el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-form>
          <el-table :data="tableData" style="width: 100%">
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
        </div>
        <!-- 规格参数 -->
        <div>
          <div class="step-title">
            <i class="el-icon-edit-outline"></i> 规格参数（可编辑表格）
          </div>
          <el-form ref="form" :model="form" label-width="90px">
            <el-form-item label="活动区域">
              <el-select v-model="form.region" placeholder="请选择活动区域">
                <el-option label="区域一" value="shanghai"></el-option>
                <el-option label="区域二" value="beijing"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="活动区域">
              <el-select v-model="form.region" placeholder="请选择活动区域">
                <el-option label="区域一" value="shanghai"></el-option>
                <el-option label="区域二" value="beijing"></el-option>
              </el-select>
            </el-form-item>
          </el-form>
        </div>
        <!-- 商品相册 -->
        <div>
          <div class="step-title">
            <i class="el-icon-edit-outline"></i> 商品相册
          </div>
          <el-upload
            class="upload-demo"
            action="https://jsonplaceholder.typicode.com/posts/"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :file-list="fileList"
            list-type="picture">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </div>
        <!-- 下一步 -->
        <div class="category-next text-center">
          <el-button  @click.native="step=2">上一步，填写商品属性</el-button>
          <el-button type="primary"  @click.native="addGoods">下一步，完成商品添加</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { getRootCategory, getSecondCategory, getThreeCategory } from '@/api/category'

@Component
export default class AddGoods extends Vue {
  private step = 1  // 当前步骤
  private category = {
    rootItem: [],   // 一级分类
    secondItem: [], // 一级分类
    threeItem: [],  // 三级级分类
    rootIndex: 0,
    secondIndex: 0,
    threeIndex: 0
  }
  private spuTempalte = {
    sku: {},    // josn
    detail: [], // array
    specs: {}   // json
  }
  // ------------ test 
  private textarea = ''
  private form = {
          name: '',
          region: '',
          date1: '',
          date2: '',
          delivery: false,
          type: [],
          resource: '',
          desc: ''
        }
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
  private fileList = [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}]

  private handleRemove(file: any, fileList: any) {
    console.log(file, fileList)
  }
  private handlePreview(file: any) {
    console.log(file)
  }
  // ---------test----------
  private addGoods() {
    alert('添加商品成功')
  }
  // 设置目录
  private initCategory(root: any, second: any, three: any) {
    if (root !== null) {
      this.category.rootItem = root
      this.category.rootIndex = 0
    }
    if (second !== null) {
      this.category.secondItem = second
      this.category.secondIndex = 0
    }
    this.category.threeItem = three
    this.category.threeIndex = 0
  }
  // 点击一级目录
  private rootClick(index: number) {
    const category: any = this.category.rootItem[index]
    getSecondCategory(category.id).then((res: any) => {
      this.$log.info('rootClick', res)
      const { second, three } = res.data
      this.initCategory(null, second, three)
      this.category.rootIndex = index
    })
  }
  // 点击二级目录
  private secondClick(index: number) {
    const category: any = this.category.secondItem[index]
    getThreeCategory(category.id).then((res: any) => {
      this.$log.info('secondClick', res)
      this.initCategory(null, null, res.data)
      this.category.secondIndex = index
    })
  }
  // 重新选择
  private reselectRoot(index: number) {
    this.step = 1
    this.rootClick(index)
  }
  private reselectSecond(index: number) {
    this.step = 1
    this.rootClick(index)
  }
  
  private mounted() {
    getRootCategory().then((res: any) => {
      const { root, second, three } = res.data
      this.initCategory(root, second, three)
    })
  }
}
</script>

<style scoped lang="scss">
.step-title {
  padding: 10px 0;
}
.choose-category {
  .category-list {
    .category-item {
      width: 230px;
      display: inline-block;
      margin-right: 20px;
      border: 1px solid #000;
      .m-list-group {
        height: 300px;
        overflow: auto;
      }
    }
    .catolog-item-btn {
      display: inline-block;
      line-height: 100px;
      position: relative;
      width: 60px;
      button {
        position: absolute;
        bottom: 130px;
      }
    }
  }
}
// .fill-info {
  
// }
.fill-attribute {
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
}
.m-active {
  display: block !important;
}
</style>
