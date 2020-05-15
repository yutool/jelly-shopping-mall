<template>
  <div>
    <div class="banner-wrapper">
      <!-- 轮播图 -->
      <div class="slideshow">
        <el-carousel trigger="click" height="450px">
          <el-carousel-item v-for="item in 3" :key="item">
            <h3 class="small">{{ item }}</h3>
          </el-carousel-item>
        </el-carousel>
      </div>
      <!-- 菜单 -->
      <div class="category">
        <ul class="category-list">
          <li v-for="root in menu.root" :key="root.id">
            {{ root.name }}
          </li>
        </ul>
      </div>
    </div>
    <!-- 商品列表 -->
    <div class="container">
      <div v-for="id in contentMenu.menus" :key="id">
        <!-- 推荐菜单 -->
        <div>
          <ul class="content-menu">
            <li v-for="item in contentMenu.content[id]" :key="item">
              {{ item }}
            </li>
          </ul>
        </div>
        <!-- 商品 -->
        <el-row :gutter="20">
          <el-col :md="6" v-for="spu in content[id]" :key="spu.id">
            <goods-card :spu="spu" />
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import GoodsCard from './components/GoodsCard.vue'
import { getSpuList } from '@/api/spu'
import { getMenu, getContentMenu, getContent } from '@/api/home'

@Component({
  components: {
    GoodsCard
  }
})
export default class GoodsList extends Vue {
  private menu = {}
  private contentMenu: any = {}
  private content = {}
  
  // 获取
  private getMenu() {
    getMenu().then((res: any) => {
      this.menu = res.data
      this.$log.info('获取菜单', res)
    })
  }
  // 获取首页内容
  private getContent() {
    getContentMenu().then((res: any) => {
      this.contentMenu = res.data
      this.$log.info('获取推荐菜单', this.contentMenu)
      getContent(res.data.menus).then((res: any) => {
        this.content = res.data
        this.$log.info('获取内容', res)
      })
    })
  }
  private mounted() {
    this.getMenu()
    this.getContent()
  }
}
</script>

<style scoped lang="scss">
.banner-wrapper {
  position: relative;
}
.category {
  position: absolute;
  top: 0;
  left: 0;
  z-index: 99;
  margin-left: 100px;
  .category-list {
    height: 450px;
    background: rgba(255, 255, 255, .1);
    padding: 0;
    li {
      list-style: none;
      padding: 3px 30px;
      cursor: pointer;
    }
    li:hover {
      background: rgb(255, 247, 247);
    }
  }
}
.content-menu {
  padding: 0;
  li {
    list-style: none;
    display: inline;
    padding: 0 5px;
  }
}
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  height: 100px;
  line-height: 150px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>