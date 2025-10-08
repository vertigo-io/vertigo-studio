<template>
  <div class="shiny-photo-container">
    <h3 class="shiny-component-title">{{ data.title || 'Shiny Photo' }}</h3>
    <div class="photo-wrapper">
      <img v-if="data.url" :src="data.url" :alt="data.alt" class="shiny-photo-image">
      <div v-else class="no-image-message">No image URL provided.</div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'VShinyPhotoComponent',
  props: {
    data: {
      type: Object,
      required: true,
    },
  },
});
</script>

<style scoped>
.shiny-photo-container {
  background-color: #1A202C;
  padding: 15px;
  border-radius: 8px;
  color: #CBD5E0;
  text-align: center;
}

.shiny-component-title {
  color: #E2E8F0;
  margin-bottom: 15px;
}

.photo-wrapper {
  width: 100%;
  max-width: 600px; /* Adjust as needed */
  margin: 0 auto;
}

.shiny-photo-image {
  max-width: 100%;
  height: auto;
  display: block;
  border-radius: 8px;
}

.no-image-message {
  color: #A0AEC0;
  font-style: italic;
  padding: 20px;
}
</style>