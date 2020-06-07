<template>
  <div>
    <div class="banner-wrap">
      <!-- 轮播图 -->
      <div class="slideshow">
        <el-carousel trigger="click" height="450px">
          <el-carousel-item>
            <a href="#"> <img src="@/assets/2.jpg" alt=""> </a>
          </el-carousel-item>
          <el-carousel-item>
            <a href="#"> <img src="@/assets/1.jpg" alt=""> </a>
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
        <div class="content-menu mt-3">
          <div class="sideIcon" style="background-color: #FFA1B8"></div>
          <ul>
            <li v-for="item in contentMenu.content[id]" :key="item">
              {{ item }}
            </li>
          </ul>
        </div>
        <!-- 商品 -->
        <div class="row">
          <div class="m-col" v-for="spu in content[id]" :key="spu.id">
            <goods-card :spu="spu" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import GoodsCard from './components/GoodsCard.vue'
import { getSpuList } from '@/api/goods/spu'
import { getMenu, getContentMenu, getContent } from '@/api/goods/home'

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
.banner-wrap {
  position: relative;
  img {
    width: 100%;
    overflow: hidden;
  }
}
.category {
  position: absolute;
  top: 0;
  left: 0;
  z-index: 99;
  margin-left: 100px;
  .category-list {
    height: 450px;
    background: rgba(255, 255, 255, .6);
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
  ul { padding: 0; }
  li {
    list-style: none;
    display: inline;
    padding: 0 5px;
    font-size: 15px;
    color: #666;
  }
  li:first-child {
    font-size: 20px;
  }
  li:not(:first-child):before {
    content: "|";
    margin-right: 7px;
    font-weight: 300;
  }
  .sideIcon {
    width: 8px !important;
    height: 20px !important;
    float: left;
    margin-top: 5px;
    margin-right: 10px;
  }
}

</style>