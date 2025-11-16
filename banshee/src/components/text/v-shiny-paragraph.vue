<template>
  <div class="chart-container">
    <p class="chakra-paragraph">{{ data.text }}</p>
  </div>
</template>

<script setup lang="ts">
import { ShinyParagraph } from '../../models/text/paragraph/ShinyParagraph';

defineProps<{
  data: ShinyParagraph
}>()
</script>

<style scoped>
/* All styles are now handled by the global style.css */
</style>