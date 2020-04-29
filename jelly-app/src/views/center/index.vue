<template>
  <div class="container">
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

@Component({
  components: {
    SideBar
  }
})
export default class Center extends Vue {
}
</script>

<style scoped lang="scss">

</style>
