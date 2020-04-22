<template>
  <div id="app">
    <Navbar />
    <el-row class="m-0 mt-5">
      <el-col :md="5" :sm="6">
        <Sidebar />
      </el-col>
      <el-col :md="19" :sm="18">
        <router-view/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Navbar from '@/components/Navbar'
import Sidebar from '@/components/Sidebar'
export default {
  components: {
    Navbar, Sidebar
  }
}
</script>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
</style>
