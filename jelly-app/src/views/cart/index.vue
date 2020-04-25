<template>
  <div>
    <router-view />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class Cart extends Vue {
  private active = 0
}
</script>

<style scoped lang="scss">

</style>
