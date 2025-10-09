<template>
  <div class="photo-container">
    <div class="table-title">{{ data.title || 'Photo' }}</div>
    <div class="photo-wrapper">
      <img v-if="data.url" :src="data.url" :alt="data.alt" class="photo-image">
      <div v-else class="no-image-message">No image URL provided.</div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { ShinyPhoto } from '../../models/media/photo/ShinyPhoto';

export default defineComponent({
  name: 'VShinyPhotoComponent',
  props: {
    data: {
      type: Object as () => ShinyPhoto,
      required: true,
    },
  },
});
</script>

<style scoped>
/* All styles are now handled by the global style.css */
.no-image-message {
  color: var(--chakra-paragraph-text);
  font-style: italic;
  padding: 20px;
  text-align: center;
}
</style>