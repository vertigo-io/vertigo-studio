<template>
  <div class="shiny-grid" :style="gridStyle">
    <component
      v-for="component in data.content"
      :key="component.id"
      :is="resolve(component.shinyType)"
      :data="component"
    ></component>
  </div>
</template>

<script setup lang="ts">
import { computed, inject } from 'vue';
import { ShinyGrid } from '../../models/layout/grid/ShinyGrid';
import { ShinyRegistry } from '../ShinyRegistry';

const props = defineProps<{
  data: ShinyGrid;
}>();

const shinyRegistry = inject<ShinyRegistry>('shinyRegistry');
if (!shinyRegistry) {
  throw new Error('shinyRegistry must be provided');
}

const resolve = (shinyType: string) => {
  return shinyRegistry.resolve(shinyType);
};

const gridStyle = computed(() => {
  return {
    display: 'grid',
    gridTemplateColumns: `repeat(${props.data.columns}, 1fr)`,
    gap: '16px', // You can adjust the gap as needed
  };
});
</script>

<style scoped>
.shiny-grid {
  /* Add any specific styling for the grid container */
  padding: 16px;
  border: 1px solid #eee;
  border-radius: 8px;
}
</style>
