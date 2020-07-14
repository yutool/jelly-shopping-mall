<template>
  <div class="container" v-loading.fullscreen="loading">
    <div class="sidebar">
      <side-bar />
    </div>
    <div class="content">
      <router-view />
    </div>
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
.sidebar {
  width: 200px;
  height: 100vh;
  position: fixed;
  box-shadow: 6px 0 3px -6px rgba(0, 0, 0, 0.1);
}
.content {
  float: left;
  padding-left: 220px !important;
}
</style>
