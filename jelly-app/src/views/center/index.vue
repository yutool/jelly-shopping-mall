<template>
  <div class="container mt-3" v-loading.fullscreen="loading">
    <el-row>
      <el-col :md="5" :sm="7" :xs="9">
        <side-bar class="border" />
      </el-col>
      <el-col :md="19" :sm="17" :xs="15">
        <router-view />
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import SideBar from './components/SideBar.vue'
import { State } from 'vuex-class'

@Component({
  components: {
    SideBar
  }
})
export default class Center extends Vue {
  @State((state: any) => state.app.loading) private loading: any
}
</script>

<style scoped lang="scss">

</style>
