<template>
  <div>
    <cart-item v-for="item in cartList" :key="item.id" :cart="item" class="mb-2"/>
    <div class="cart-pay card">
      购买
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator'
import CartItem from './CartItem.vue'


@Component({
  components: {
    CartItem
  }
})
export default class AllOrder extends Vue {
  @Prop() private cartList: any
}
</script>

<style scoped lang="scss">

</style>
