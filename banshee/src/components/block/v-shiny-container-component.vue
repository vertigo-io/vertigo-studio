<template>
  <div class="shiny-container">
    <div class="container-content">
      <template v-for="(component, index) in data.content" :key="index">
        <!-- TEST-->
        <component 
        :is="shinyRegistry.resolve(component.shinyType)" 
        :data="component"></component>
      </template>
    </div>
  </div>
</template>

<script setup lang="ts">
import { inject } from 'vue';
import { ShinyContainer } from '../../models/block/ShinyContainer';
import { ShinyRegistry } from '../ShinyRegistry';

const props = defineProps<{
  data: ShinyContainer
}>()

const shinyRegistry = inject<ShinyRegistry>('shinyRegistry')!;
</script>

<style scoped>
.shiny-container {
  border: 1px solid var(--assistant-accent);
  padding: 10px;
  margin: 10px 0;
  border-radius: 8px;
  background-color: var(--general-surface);
}

.container-title {
  font-size: 1.2em;
  font-weight: bold;
  margin-bottom: 10px;
  color: var(--chakra-title-text);
}

.container-content {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.placeholder-component {
  border: 1px dashed var(--general-text);
  padding: 10px;
  background-color: var(--general-bg);
  color: var(--general-text);
  min-width: 150px;
  text-align: center;
}
</style>