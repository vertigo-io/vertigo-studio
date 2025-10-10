<template>
  <div class="chart-container">
    <component :is="`h${data.level || 1}`" class="component-title">{{ data.title || 'Default Title' }}</component>
  </div>
</template>

<script setup lang="ts">
import { ShinyTitle } from '../../models/text/title/ShinyTitle';

defineProps<{
  data: ShinyTitle
}>()
</script>

<style scoped>
.component-title {
  color: var(--assistant-text);
  margin: 0;
  padding: 0;
}
</style>