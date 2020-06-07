<template>
  <div class="container">
    <el-page-header @back="$router.back()" content="添加秒杀商品">
    </el-page-header>
    <div class="seckill-form">
      <el-form ref="seckillForm" :model="seckillForm" :rules="rules" label-width="80px">
        <el-form-item label="商品标题">
          <el-input v-model="spu.title" disabled></el-input>
        </el-form-item>
        <!-- sku列表 -->
        <div class="table-responsive-lg">
          <table v-if="JSON.stringify(sku) !== '{}'" class="table">
            <thead>
              <tr>
                <th scope="col">#</th>
                <th>封面</th>
                <th scope="col" v-for="key in Object.keys(spu.skuTemplate)" :key="key">
                  {{ key }}
                </th>
                <th>单价</th>
                <th>剩余库存</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item, i) in sku" :key="item.id">
                <th scope="row">
                  <input class="ckeckBoxGroup" type="checkbox" @change="checkChange(item, i)">
                </th>
                <td> <img :src="item.image" width="30px" alt="封面" /> </td>
                <td v-for="i in Object.keys(spu.skuTemplate)" :key="i">
                  {{ item.sku[i] }}
                </td>
                <td>{{ item.price }}</td>
                <td>{{ item.num }}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <!-- 其他信息 -->
        <el-form-item label="活动时间" prop="startTime">
          <el-date-picker v-model="seckillForm.startTime" type="date" value-format="yyyy/MM/dd">
          </el-date-picker>
          <span class="pl-1 pr-1"> - </span>
          <el-date-picker v-model="seckillForm.endTime" type="date" value-format="yyyy/MM/dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="时间段" prop="region">
          <el-select v-model="seckillForm.region" placeholder="选择秒杀时间段">
            <el-option v-for="i in 12" :key="i" :value="(i-1) * 2 "
            :label="(i-1) * 2 + ' - ' + i * 2 + ' 时间段'" >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="秒杀价格" prop="price">
          <el-input v-model="seckillForm.price" style="width: 220px"></el-input>
        </el-form-item>
        <el-form-item label="秒杀数量" prop="num">
          <el-input v-model="seckillForm.num" style="width: 220px"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submit">立即添加</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Ref } from 'vue-property-decorator';
import { getGoods } from '@/api/spu'
import { addSeckillGodos } from '@/api/seckill'
import { Form } from 'element-ui'

@Component
export default class AddSeckill extends Vue {
  @Ref('seckillForm') private refSeckillForm!: Form
  private spu: any = {}
  private sku: any = {}
  private checkSku: any = {}
  private seckillForm = {
    id: '',
    title: '',
    originalPrice: '',
    price: '',
    num: '',
    startTime: '',
    endTime: '',
    region: ''
  }
  private rules = {
    startTime: [
      { required: true, message: '请选择秒杀日期', trigger: 'change' },
    ],
    region: [
      { required: true, message: '请选择秒杀时间段', trigger: 'change' },
    ],
    price: [
      { required: true, message: '请输入秒杀价格', trigger: 'change' },
    ],
    num: [
      { required: true, message: '请输入秒杀数量', trigger: 'change' },
    ]
  }
  
  // 切换商品
  private checkChange(sku: any, index: number) {
    $('.ckeckBoxGroup').each((i: any, e: any) => {
      if (i !== index) {
        e.checked = false
      }
    })
    this.checkSku = sku // 需要查询是否已存在秒杀商品中
  }
  // 提交表单
  private submit() {
    if (JSON.stringify(this.checkSku) === '{}') {
      this.$message({ type: 'info', message: '请选择商品' })
      return
    }
    this.refSeckillForm.validate((valid: any) => {
      if (valid) {
        this.seckillForm.title = this.spu.title
        this.seckillForm.id = this.checkSku.id
        this.seckillForm.originalPrice = this.checkSku.price
        addSeckillGodos(this.seckillForm).then((res: any) => {
          this.$log.info('增加秒杀商品', res)
          this.$message({type: 'success', message: res.message})
          this.$router.go(0)
        })
      } else {
        console.log('error submit!!');
        return false;
      }
    })
  }
  // 查询商品
  private getGoods() {
    getGoods(this.$route.params.id).then((res: any) => {
      this.spu = res.data.spu
      this.sku = res.data.sku
      this.spu.skuTemplate = JSON.parse(this.spu.skuTemplate)
      for (const item of this.sku) {
        item.sku = JSON.parse(item.sku)
      }
      this.$log.info('查询商品信息', res)
    })
  }
  private mounted() {
    this.getGoods()
  }
}

</script>

<style scoped lang="scss">
.seckill-form {
  padding: 20px;
}
.table-responsive-lg {
  th:not(:first-child) {
    min-width: 100px;
  }
}
</style>
