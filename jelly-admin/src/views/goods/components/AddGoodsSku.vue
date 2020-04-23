<template>
  <div>
    <!-- 商品sku -->
    <div>
      <div class="step-title">
        <i class="el-icon-edit-outline"></i> 商品属性
      </div>
      <el-form ref="form" label-width="90px">
        <el-form-item v-for="(value, name) in skuTemplate" :key="name"  :label="name" >
          <el-checkbox-group v-model="skuArr[name]" >
            <el-checkbox v-for="item in value" :key="item" @change="generateSku" :label="item"></el-checkbox>
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
      <el-table :data="skuData" style="width: 100%">
        <!-- 表头 -->
        <el-table-column :label="'商品名称'" :prop="'商品名称'">
        </el-table-column>
        <el-table-column v-for="(value, name) in skuTemplate" :key="name"
          :label="name" :prop="name">
        </el-table-column>
        <!-- 内容在 :data自动遍历 -->
        <!-- 折叠内容 -->
        <el-table-column type="expand">
          <el-form v-for="(item,index) in skuData" :key="index" label-position="left" inline class="demo-table-expand">
            <el-form-item  v-for="(value,name) in item" :label="name" :key="name">
              <span>{{ value }}</span>
            </el-form-item>
          </el-form>
        </el-table-column>
      </el-table>
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
      <el-button  @click="prevStep">上一步，填写商品属性</el-button>
      <el-button type="primary"  @click="nextStep">下一步，完成商品添加</el-button>
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
  private skuData = [{          // 全排生成
    商品名称: 'fs',
    套餐类型: 'as',
    存储类型: 'as',
    机身颜色: 'as',
    网络类型: 'as',
    other: 'fds'
  }]
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
  }
  private addAttrItem(name: string, e: any) {
    const inputValue = $(e.currentTarget).prev().val()
    if (inputValue === '' || this.skuTemplate[name].indexOf(inputValue) !== -1) {
      return
    }
    this.skuTemplate[name].push(inputValue)
  }
  private generateSku() {
    const flagArr: any = []
    const resultArr: any = []
    this.dfs(flagArr, resultArr)
    console.log('-----------------')
  }
  private dfs(flagArr: any, resultArr: any) {
    if (resultArr.length === Object.keys(this.skuArr).length) {
      console.log(resultArr)
    }
    for (const key of Object.keys(this.skuArr)) {
      if (flagArr.indexOf(key) === -1 && // 可以不存在且key只能顺序添加
      flagArr.length === Object.keys(this.skuArr).indexOf(key)) {
        flagArr.push(key)
        for (const item of this.skuArr[key]) { // 一行
          resultArr.push(item)
          this.dfs(flagArr, resultArr)
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
    this.$emit('nextStep', this.skuTemplate, this.spuSpecs)
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
