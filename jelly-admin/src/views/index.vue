<template>
  <div class="container">
    Lorem ipsum dolor sit amet consectetur adipisicing elit. Ducimus ipsam aperiam beatae doloribus accusantium illum neque soluta, perferendis est consequuntur voluptate temporibus distinctio harum reprehenderit adipisci praesentium quod, fugiat animi.
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import Sidebar from '@/components/Sidebar.vue';

@Component({
  components: {
    Sidebar
  }
})
export default class Market extends Vue {
}
</script>

<style scoped lang="scss">
</style>
