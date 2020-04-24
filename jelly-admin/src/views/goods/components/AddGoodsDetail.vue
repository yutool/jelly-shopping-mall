<template>
  <div>
    <!-- 商品详情 -->
    <div>
      <div class="step-title">
        <i class="el-icon-edit-outline"></i> 商品详情（可编辑表格）
      </div>
      <table class="table">
        <thead>
          <tr>
            <th scope="col">#</th> <th scope="col">名称</th> 
            <th scope="col">值</th> <th scope="col">操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(value, name, index) in spuDetail" :key="name">
            <th scope="row">{{index+1}}</th>
            <td> {{name}} </td>
            <td> <input type="text" v-model="spuDetail[name]"> </td>
            <td> <el-button type="danger" size="small" @click.native="deleteDetail(name)">删除</el-button> </td>
          </tr>
          <tr>
            <td colspan="4">
              <el-input v-model="inputDetail" placeholder="请输入内容" style="width:300px"></el-input>
              <el-button @click.native="addDetail">添加属性</el-button>
            </td>
          </tr>
        </tbody>
      </table>
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
        :file-list="images"
        list-type="picture">
        <el-button size="small" type="primary">点击上传</el-button>
        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
      </el-upload>
    </div>
    <!-- 下一步 -->
    <div class="category-next text-center">
      <el-button  @click="prevStep">上一步，选择商品分类</el-button>
      <el-button type="primary" @click="nextStep">下一步，填写商品属性</el-button>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';

@Component
export default class AddGoodsDetail extends Vue {
  @Prop() private category: any
  @Prop() private spuTemplate: any
  
  private images = []
  private inputDetail = ''
  private spuDetail: any = {}
 
  // 添加记录
  private addDetail() {
    if (this.inputDetail === '' || this.spuDetail.hasOwnProperty(this.inputDetail)) {
      return
    }
    Vue.set(this.spuDetail, this.inputDetail, '')
  }
  // 添加记录
  private deleteDetail(name: string) {
    Vue.delete(this.spuDetail, name)
  }
  
  // 事件
  private prevStep() {
    this.$emit('prevStep')
  }
  private nextStep() {
    // 相册未传入
    this.$emit('nextStep', this.spuDetail)
  }
  
  private arr2Obj(arr: any) {
    const obj: any = {}
    for (const i of arr) {
      obj[i] = ''
    }
    return obj
  }
  // 初始化商品详情模板
  private initSpuTemplate() {
    if (this.spuTemplate === null) {
      this.spuDetail = {}
      return
    }
    this.spuDetail = this.arr2Obj(this.spuTemplate.detail)
  }
  
  private mounted() {
    this.initSpuTemplate()
  }
  
  // ------ 图片上传 ------
  private handleRemove(file: any, fileList: any) {
    console.log(file, fileList)
  }
  private handlePreview(file: any) {
    console.log(file)
  }
  
}
</script>

<style scoped lang="scss">

</style>
