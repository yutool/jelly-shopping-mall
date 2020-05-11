<template>
  <div class="container">
    <el-page-header @back="$router.back()" content="添加秒杀商品">
    </el-page-header>
    <div class="seckill-form">
      <el-form ref="form" :model="form" label-width="80px">
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
        <el-form-item label="活动时间">
          <el-date-picker type="date" placeholder="选择日期" v-model="form.startTime"></el-date-picker>
          <span class="pl-2 pr-2"> - </span>
          <el-date-picker type="date" placeholder="选择日期" v-model="form.endTime"></el-date-picker>
        </el-form-item>
        <el-form-item label="时间段">
          <el-select v-model="form.region" placeholder="选择秒杀时间段">
            <el-option v-for="i in 6" :key="i" :value="(i-1) * 2 "
            :label="(i-1) * 2 + ' - ' + i * 2 + ' 时间段'" >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="秒杀价格">
          <el-input v-model="form.price" style="width: 220px"></el-input>
        </el-form-item>
        <el-form-item label="秒杀数量">
          <el-input v-model="form.num" style="width: 220px"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submit">立即添加</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { getGoods } from '@/api/spu'
import { addSeckillGodos } from '@/api/seckill'

@Component
export default class AddSeckill extends Vue {
  private spu: any = {}
  private sku: any = {}
  private checkSku: any = {}
  private form = {
    skuId: '',
    title: '',
    price: '',
    num: '',
    costPrice: '',
    startTime: '',
    endTime: '',
    region: ''
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
    this.form.title = this.spu.title
    this.form.skuId = this.checkSku.id
    this.form.costPrice = this.checkSku.price
    console.log(this.form)
    
    addSeckillGodos(this.form).then((res: any) => {
      this.$log.info('增加秒杀商品', res)
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
