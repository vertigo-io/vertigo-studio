<template>
  <div class="shiny-video-container">
    <h3 class="shiny-component-title">{{ data.title || 'Shiny Video' }}</h3>
    <div class="video-player-wrapper">
      <iframe
        v-if="data.videoId"
        class="video-player"
        width="100%"
        height="315"
        :src="'https://www.youtube.com/embed/' + data.videoId"
        frameborder="0"
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
        allowfullscreen
      ></iframe>
      <div v-else class="no-video-message">No video ID provided.</div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'VShinyVideoComponent',
  props: {
    data: {
      type: Object,
      required: true,
    },
  },
});
</script>

<style scoped>
.shiny-video-container {
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

.video-player-wrapper {
  position: relative;
  width: 100%;
  padding-bottom: 56.25%; /* 16:9 Aspect Ratio */
  height: 0;
}

.video-player {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  border-radius: 8px;
}

.no-video-message {
  color: #A0AEC0;
  font-style: italic;
  padding: 20px;
}
</style>