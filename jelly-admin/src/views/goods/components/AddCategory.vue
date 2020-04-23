<template>
  <div class="choose-category">
    <!-- 选择分类 -->
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
            v-for="(c,index) in category.threeItem" :key="c.id" @click="threeClick(index)">
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
    <!-- 下一步 -->
    <div class="category-next text-center">
      <el-button type="primary" @click="nextStep">下一步，填写商品信息</el-button>
    </div>
  </div>
</template>

<script lang="ts">
import { getRootCategory, getSecondCategory, getThreeCategory } from '@/api/category'
import { Component, Vue, Emit } from 'vue-property-decorator';
import { State } from 'vuex-class';

@Component
export default class AddCategory extends Vue {
  private category = {
    rootItem: [],   // 一级分类
    secondItem: [], // 一级分类
    threeItem: [],  // 三级级分类
    rootIndex: 0,
    secondIndex: 0,
    threeIndex: 0
  }
  private spuTemplate = null
  // ------ 选择目录 ------
  private rootClick(index: number) {
    const category: any = this.category.rootItem[index]
    getSecondCategory(category.id).then((res: any) => {
      this.$log.info('rootClick', res)
      const { second, three } = res.data
      this.initCategory(null, second, three, 0)
      this.category.rootIndex = index
    })
  }
  private secondClick(index: number) {
    const category: any = this.category.secondItem[index]
    getThreeCategory(category.id).then((res: any) => {
      this.$log.info('secondClick', res)
      this.initCategory(null, null, res.data, 0)
      this.category.secondIndex = index
    })
  }
  private threeClick(index: number) {
    this.initCategory(null, null, null, index)
  }
  // 事件
  private nextStep() {
    this.$emit('nextStep', this.category, this.spuTemplate)
  }
  
  // ------ 工具函数 ------
  private initCategory(root: any, second: any, three: any, index: number) {
    if (root !== null) {
      this.category.rootItem = root
      this.category.rootIndex = 0
    }
    if (second !== null) {
      this.category.secondItem = second
      this.category.secondIndex = 0
    }
    if (three !== null) {
      this.category.threeItem = three
    }
    this.category.threeIndex = index
    
    const category: any = this.category.threeItem[index]
    if (category.template) {
      this.spuTemplate = JSON.parse(category.template)
    } else {
      this.spuTemplate = null
    }
    
    
    if (category.template) { // 设置模板
      // const template = JSON.parse(category.template)
      // this.spuDetail = this.arr2Obj(template.detail)
      // this.spuSku = template.sku
      // for (const key of Object.keys(this.spuSku)) {
      //   this.skuArr[key] = []
      // }
      // this.skuArr = JSON.stringify(this.skuArr)
      // this.skuArr = JSON.parse(this.skuArr)
      // this.spuSpecs = template.specs
      
      // for (const key of Object.keys(this.spuSpecs)) {
      //   this.spuSpecs[key] = this.arr2Obj(this.spuSpecs[key])
      // }
    } else {
      // this.spuDetail = {}
    }
  }
  private arr2Obj(arr: any) {
    const obj: any = {}
    for (const i of arr) {
      obj[i] = ''
    }
    return obj
  }
  
  private mounted() {
    getRootCategory().then((res: any) => {
      const { root, second, three } = res.data
      this.initCategory(root, second, three, 0)
    })
  }
}
</script>

<style scoped lang="scss">
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
</style>
