<template>
  <div class="container">
    <el-steps :active="step" simple>
      <el-step title="选择商品分类" icon="el-icon-tickets"></el-step>
      <el-step title="填写商品信息" icon="el-icon-edit"></el-step>
      <el-step title="填写商品属性" icon="el-icon-sell"></el-step>
    </el-steps>
    <div>
      <!-- 步骤 1 -->
      <div v-if="step==1">
        <!-- 选择分类 -->
        <add-category @nextStep="nextOf1" />
      </div>
      <!-- 步骤 2 -->
      <div v-if="step==2" class="fill-info">
        <!-- 基本信息 -->
        <div class="goods-info">
          <div class="step-title">
            <i class="el-icon-edit-outline"></i> 基本信息
          </div>
          <el-form ref="form" :model="spu" label-width="80px">
            <el-form-item label="商品分类">
              <span>{{category.rootItem[category.rootIndex].name}}</span> &gt; 
              <span>{{category.secondItem[category.secondIndex].name}}</span> &gt; 
              <span>{{category.threeItem[category.threeIndex].name}}</span>
            </el-form-item>
            <el-form-item label="商品名称">
              <el-input v-model="spu.name"></el-input>
            </el-form-item>
            <el-form-item label="商品标题">
              <el-input v-model="spu.title"></el-input>
            </el-form-item>
            <el-form-item label="商品品牌">
              <el-select v-model="spu.brand" placeholder="请选择活动区域">
                <el-option label="小米" value="1"></el-option>
                <el-option label="Apple" value="2"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="服务保证">
              <el-checkbox-group v-model="serve">
                <el-checkbox label="七天无理由" name="七天无理由"></el-checkbox>
                <el-checkbox label="快速退货" name="快速退货"></el-checkbox>
                <el-checkbox label="免费包邮" name="免费包邮"></el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-form>
        </div>
        <!-- 详情信息 -->
        <add-goods-detail :category="category" :spuTemplate="spuTemplate" @prevStep="prevOf2" @nextStep="nextOf2" />
      </div>
      <!-- 步骤 3 -->
      <div v-if="step==3" class="fill-attribute">
        <!-- 商品属性 -->
        <add-goods-sku :spuTemplate="spuTemplate" @prevStep="prevOf3" @nextStep="nextOf3" />
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import AddCategory from './components/AddCategory.vue'
import AddGoodsDetail from './components/AddGoodsDetail.vue'
import AddGoodsSku from './components/AddGoodsSku.vue'
import { addGoods } from '@/api/spu'
import { copyObj } from '@/common/utils/ObjectUtil'

@Component({
  components: {
    AddCategory, AddGoodsDetail, AddGoodsSku
  }
})
export default class AddGoods extends Vue {
  private step = 1            // 当前步骤
  private category = {}       // 当前选择的目录
  private spuTemplate = null  // 当前目录的模板
  private serve = []          // 选择的服务
  private spu = {             // 提交到后台的商品信息
    name: '',
    title: '',
    brand: '',
    category1Id: '',
    category2Id: '',
    category3Id: '',
    picture: '',
    detail: {},
    serve: '',
    skuTemplate: {},
    specs: {},
  }
  private skuList: any = []
  // ------ 事件监听 ------ 
  private nextOf1(category: any, spuTemplate: any) {
    this.category = category
    this.spuTemplate = spuTemplate
    this.spu.category1Id = category.rootItem[category.rootIndex].id
    this.spu.category2Id = category.secondItem[category.secondIndex].id
    this.spu.category3Id = category.threeItem[category.threeIndex].id
    this.step = 2
    window.scroll(0, 0)
    console.log('addCategory信息: ', category, spuTemplate)
  }
  private prevOf2() {
    this.step = 1
  }
  private nextOf2(spuDetail: any) {
    this.spu.picture = 'http://img12.360buyimg.com/n1/s450x450_jfs/t1/45124/2/5820/397999/5d36c0cdEda359655/61f65ac6aae3146b.jpg'
    this.spu.detail = JSON.stringify(spuDetail)
    this.spu.serve = this.serve + ''
    console.log('addDetail信息', spuDetail, this.spu.serve)
    this.step = 3
    window.scroll(0, 0)
  }
  private prevOf3() {
    this.step = 2
  }
  private nextOf3(skuTemplate: any, skuList: any, spuSpecs: any) {
    console.log('addGoodsSKu信息：', skuTemplate, skuList, skuList)
    // 添加商品，数据库保存为JSON格式，这里要序列化
    this.spu.skuTemplate = JSON.stringify(skuTemplate)
    this.spu.specs = JSON.stringify(spuSpecs)
    // 将sku保存为JSON格式
    this.skuList = copyObj(skuList)
    for (const sku of this.skuList) {
      sku.sku = JSON.stringify(sku.sku)
    }
    // 添加到数据库
    addGoods({spu: this.spu, sku: this.skuList}).then((res: any) => {
      this.$log.info('添加商品res', res)
      this.$message({type: 'success', message: '添加商品成功'})
      this.$router.go(0)
    })
  }
}
</script>

<style scoped lang="scss">
.step-title {
  padding: 10px 0;
}
</style>
