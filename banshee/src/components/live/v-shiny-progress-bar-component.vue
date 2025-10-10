<template>
  <div class="progress-container" :id="data.id">
    <div class="progress-bar" :style="{ width: percentage + '%' }"></div>
    <div class="progress-text">{{ Math.round(percentage) }}%</div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';

const props = defineProps<{
  data: { id: string; value: number; total: number }
}>()

const percentage = computed((): number => {
  return Math.min((props.data.value / props.data.total) * 100, 100);
});
</script>

<style scoped>
/* All styles are now handled by the global style.css */
</style>