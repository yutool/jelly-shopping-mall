<template>
  <div class="container">
    <ul class="hoursMenu clearfix">
      <li v-for="(menu, i) in menus" :key="menu" @click="changeMenu(i)"
        :class="{active: i==0}">
        <div class="clearfix">
          <el-col :span="10" class="menu-time">
            {{menu.substring(8)}} : 00
          </el-col>
          <el-col :span="14" v-if="active == i" class="menu-show">
            <div v-if="i == 0">
              <div>正在秒杀</div>
              <div>距离结束：xxx</div>
            </div>
            <div v-else>
              <div>即将开始</div>
              <div>距离开始：xxx</div>
            </div>
          </el-col>
          <el-col :span="14" v-else >
            <div v-if="i == 0" >
              <span class="menu-hint">进行中</span>
            </div>
            <div v-else>
              <span class="menu-hint">即将开始</span>
            </div>
          </el-col>
        </div>
      </li>
    </ul>
    <el-row :gutter="20">
      <el-col v-for="goods in goodsList" :key="goods.spu.id" :md="6">
        <!-- 首页显示第一个sku -->
        <goods-card :goods="goods" :time="menus[active]" />
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import GoodsCard from './components/GoodsCard.vue'
import { getDateMenu, getGoodsList } from '@/api/seckill/seckill'

@Component({
  components: {
    GoodsCard
  }
})
export default class SeckillList extends Vue {
  private active = 0
  private menus: any = []
  private goodsList: any = []
  
  // 切换菜单
  private changeMenu(index: number) {
    this.active = index
    this.getGoods(this.menus[index]);
    $('.hoursMenu li').each((i: any, e: any) => {
      if (i !== index) {
        e.classList.remove('active')
      } else {
        e.classList.add('active')
      }
    })
  }
  
  // 获取秒杀菜单
  private getDateMenu() {
    getDateMenu().then((res: any) => {
      this.menus = res.data
      this.getGoods(this.menus[0])
      this.$log.info('获取菜单', res.data)
    })
  }
  
  // 获取菜单对应的秒杀商品
  private getGoods(menu: string) {
    getGoodsList(menu).then((res: any) => {
      this.goodsList = res.data
      this.$log.info('秒杀商品', res.data)
    })
  }
  
  private mounted() {
    this.getDateMenu()
  }
}
</script>

<style scoped lang="scss">
.hoursMenu {
  padding: 0;
  box-shadow: 1px 1px 1px 1px #666;
  li {
    float: left;
    list-style: none;
    width: 20%;
    height: 60px;
    cursor: pointer;
  }
  li:hover {
    background: rgba(255, 205, 205, .3);   
  }
  .active {
    background: rgb(255, 205, 205) !important;   
  }
  .menu-time {
    padding: 15px;
  }
  .menu-show {
    padding-top: 5px;
  }
  .menu-hint {
    display: inline-block;
    border: 1px solid #666;
    border-radius: 10px;
    padding: 1px 10px;
    margin-top: 15px;
  }
}
</style>
