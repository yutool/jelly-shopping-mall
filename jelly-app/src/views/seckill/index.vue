<template>
  <div v-loading.fullscreen="loading">
    <router-view />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { State } from 'vuex-class'

@Component
export default class Seckill extends Vue {
  @State((state: any) => state.app.loading) private loading: any
}
</script>

<style scoped lang="scss">

</style>
