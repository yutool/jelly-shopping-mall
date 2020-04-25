<template>
  <div class="card">
    {{ cart }}
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator'

@Component
export default class CartItem extends Vue {
  @Prop() private cart: any
}
</script>

<style scoped lang="scss">

</style>
