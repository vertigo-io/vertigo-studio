<template>
  <div class="chart-container">
    <div class="error-message">{{ data.text || 'An error occurred' }}</div>
  </div>
</template>

<script setup lang="ts">
import { ShinyError } from '../../models/core/error/ShinyError';

defineProps<{
  data: ShinyError
}>()
</script>

<style scoped>
/* All styles are now handled by the global style.css */
</style>