<template>
  <div class="progress-container" :id="data.id">
    <div class="progress-bar" :style="{ width: percentage + '%' }"></div>
    <div class="progress-text">{{ Math.round(percentage) }}%</div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'VShinyProgressBarComponent',
  props: {
    data: {
      type: Object,
      required: true,
    },
  },
  computed: {
    percentage(): number {
      return Math.min((this.data.value / this.data.total) * 100, 100);
    },
  },
});
</script>

<style scoped>
/* All styles are now handled by the global style.css */
</style>