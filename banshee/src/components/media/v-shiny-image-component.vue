<template>
  <div class="image-container">
    <div class="table-title">{{ data.title || 'Image' }}</div>
    <div class="image-wrapper">
      <img v-if="data.url" :src="data.url" :alt="data.alt" class="image-image">
      <div v-else class="no-image-message">No image URL provided.</div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ShinyImage } from '../../models/media/image/ShinyImage';

defineProps<{
  data: ShinyImage
}>()
</script>

<style scoped>
/* All styles are now handled by the global style.css */
.no-image-message {
  color: var(--chakra-paragraph-text);
  font-style: italic;
  padding: 20px;
  text-align: center;
}

.photo-container {
	width: 100%;
	max-width: 100%;
	overflow: hidden;
	border-radius: 8px;
	box-shadow: 0 4px 16px var(--general-shadow);
}

.photo-image {
	width: 100%;
	height: auto;
	display: block;
}
</style>