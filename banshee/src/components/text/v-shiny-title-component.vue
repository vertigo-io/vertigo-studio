<template>
  <div class="chart-container">
    <component :is="`h${data.level || 1}`" class="table-title">{{ data.text }}</component>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'VShinyTitleComponent',
  props: {
    data: {
      type: Object,
      required: true,
    },
  },
});
</script>

<style scoped>
/* All styles are now handled by the global style.css */
</style>