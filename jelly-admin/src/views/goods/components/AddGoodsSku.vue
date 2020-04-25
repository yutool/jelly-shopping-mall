<template>
  <div>
    <!-- 商品sku -->
    <div>
      <div class="step-title">
        <i class="el-icon-edit-outline"></i> 商品属性
      </div>
      <!-- 选择属性 -->
      <el-form ref="form" label-width="90px">
        <el-form-item v-for="(value, name) in skuTemplate" :key="name"  :label="name" >
          <el-checkbox-group v-model="skuArr[name]" >
            <el-checkbox v-for="(item, index) in value" :key="item" @change="generateSku" :label="item">
              {{item}} <a href="javascript:;" @click="delAttrItem(name, index)">删除</a>
            </el-checkbox>
          </el-checkbox-group>
          <input type="text" /> 
          <el-button @click="addAttrItem(name, $event)">添加属性</el-button>
          <el-button @click="delAttr(name)">删除属性分类</el-button>
        </el-form-item>
        <el-form-item>
          <input type="text" /> 
          <el-button @click="addAttr">添加属性分类</el-button>
        </el-form-item>
      </el-form>
      <!-- 显示属性 -->
      <table class="table">
        <thead>
          <tr> 
            <th scope="col">#</th>
            <th v-for="(value, name) in skuTemplate" :key="name">{{name}}</th>
            <th scope="col">单价</th> <th scope="col">商品库存</th>
            <th scope="col">库存预警值</th> <th scope="col">图片</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in skuData" :key="index">
            <th scope="col">{{ index+1 }}</th>
            <td v-for="val in item['sku']" :key="val"> {{val}} </td>
            <td>
              <input type="text" v-model="item['price']" style="width: 60px">
            </td>
            <td>
              <input type="text" v-model="item['num']" style="width: 60px">
            </td>
            <td>
              <input type="text" v-model="item['alertNum']" style="width: 60px">
            </td>
            <td>
              <el-button type="primary">添加图片</el-button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <!-- 商品规格 -->
    <div>
      <div class="step-title">
        <i class="el-icon-edit-outline"></i> 规格参数（可编辑表格）
      </div>
      <table class="table">
        <thead>
          <tr> 
            <th scope="col">#</th> <th scope="col">分类</th> 
            <th scope="col">值</th> <th scope="col">操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(value, name, index) in spuSpecs" :key="name"> 
            <th scope="row">{{index+1}}</th> 
            <td> {{name}} </td> 
            <td>
              <div v-for="(val, key) in value" :key="key">
                <span>{{key}}:</span>                    
                <input v-model="value[key]" />
                <el-button type="danger" size="small" @click="delSpecsItem(name,key)">删除</el-button>
              </div>
              <div>
                <input type="text">
                <el-button type="primary" size="small" @click="addSpecsItem(name,$event)">添加属性</el-button>
              </div>
            </td>
            <td>
              <el-button type="danger" size="small" @click="delSpecs(name)">删除分类</el-button>
            </td>
          </tr>
          <tr>
            <td colspan="4">
              <input placeholder="请输入内容" style="width:300px">
              <el-button @click.native="addSpecs">添加规格分类</el-button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <!-- 下一步 -->
    <div class="category-next text-center">
      <el-button  @click="prevStep">上一步，填写商品信息</el-button>
      <el-button type="primary"  @click="nextStep">提交审核</el-button>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';

@Component
export default class AddGoodsSku extends Vue {
  @Prop() private spuTemplate: any
  // sku部分
  private skuTemplate: any = {} // sku模板
  private skuArr: any = {}      // 被选中的sku
  private skuData: any = []     // 选择属性组合生成的数据
  private sku: any = {         // sku表格模板
    price: '', num: '', alertNum: '', image: 'http://img12.360buyimg.com/n1/s450x450_jfs/t1/45124/2/5820/397999/5d36c0cdEda359655/61f65ac6aae3146b.jpg' 
  }
  // 规格数据
  private spuSpecs: any = {}
  // ------ 添加属性 ------
  private addAttr(e: any) {
    const inputValue = $(e.currentTarget).prev().val()
    if (inputValue === '' || this.skuTemplate.hasOwnProperty(inputValue)) {
      return
    }
    Vue.set(this.skuTemplate, inputValue, [])
    Vue.set(this.skuArr, inputValue, [])
  }
  private delAttr(name: any) {
    Vue.delete(this.skuTemplate, name)
    Vue.delete(this.skuArr, name)
  }
  private addAttrItem(name: string, e: any) {
    const inputValue = $(e.currentTarget).prev().val()
    if (inputValue === '' || this.skuTemplate[name].indexOf(inputValue) !== -1) {
      return
    }
    this.skuTemplate[name].push(inputValue)
  }
  private delAttrItem(name: string, index: number) {
    this.skuTemplate[name].splice(index, 1)
  }
  
  private generateSku() {
    const flagArr: any = []   // 临时栈，存放属性名
    const resultArr: any = [] // 属性值数组
    this.skuData.length = 0
    this.generateDfs(flagArr, resultArr)
  }
  private generateDfs(flagArr: any, resultArr: any) {
    const keys = Object.keys(this.skuArr)
    if (resultArr.length === keys.length) {
      const skuItem: any = { sku: {}, price: '', num: '', alertNum: '', image: '' }
      const skuJson: any = {}
      // sku数组转成Json对象
      for (let i = 0; i < keys.length; i++) {
        skuJson[keys[i]] = resultArr[i]
      }
      skuItem.sku = skuJson
      this.skuData.push(skuItem)
    }
    for (const key of keys) {
      // key不存在且key只能顺序添加
      if (flagArr.indexOf(key) === -1 && flagArr.length === keys.indexOf(key)) {
        flagArr.push(key)
        for (const item of this.skuArr[key]) { // 一行
          resultArr.push(item)
          this.generateDfs(flagArr, resultArr)
          resultArr.pop()
        }
        flagArr.pop(key)
      }
    }
  }

  // ------ 操作规格 ------
  private addSpecs(e: any) {
    // 获取当前按钮的前一个兄弟节点
    const inputValue = $(e.currentTarget).prev().val()
    if (inputValue === '' || this.spuSpecs.hasOwnProperty(inputValue)) {
      return
    }
    Vue.set(this.spuSpecs, inputValue, {})
  }
  private delSpecs(name: string) {
    Vue.delete(this.spuSpecs, name)
  }
  private addSpecsItem(name: string, e: any) {
    // 获取当前按钮的前一个兄弟节点
    const inputValue = $(e.currentTarget).prev().val()
    if (inputValue === '' || this.spuSpecs[name].hasOwnProperty(inputValue)) {
      return
    }
    Vue.set(this.spuSpecs[name], inputValue, '')
  }
  private delSpecsItem(name: number, key: string) {
    Vue.delete(this.spuSpecs[name], key)
  }
  
  // ------ 事件 ------
  private prevStep() {
    this.$emit('prevStep')
  }
  private nextStep() {
    this.$emit('nextStep', this.skuTemplate, this.skuData, this.spuSpecs)
  }
  
  // ------ 初始化 ------
  private initSpuTemplate() {
    if (this.spuTemplate === null) {
      return
    }
    // 初始化sku模板
    this.skuTemplate = this.spuTemplate.sku
    for (const key of Object.keys(this.skuTemplate)) {
      this.skuArr[key] = []
    }
    // 没有转化下无法复选框无法勾选
    this.skuArr = JSON.stringify(this.skuArr)
    this.skuArr = JSON.parse(this.skuArr)
    
    // 初始化规格模板
    this.spuSpecs = this.spuTemplate.specs
    for (const key of Object.keys(this.spuSpecs)) {
      this.spuSpecs[key] = this.arr2Obj(this.spuSpecs[key])
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
    this.initSpuTemplate()
  }
}
</script>

<style scoped lang="scss">
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
