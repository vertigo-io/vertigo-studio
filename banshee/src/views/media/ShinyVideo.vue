<template>
  <div class="youtube-container">
    <div class="table-title">{{ data.title || 'Video' }}</div>
    <div class="youtube-container">
      <iframe
        v-if="data.videoId"
        class="youtube-player"
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

<script setup lang="ts">

defineProps<{
  data: { title?: string; videoId?: string }
}>()
</script>

<style scoped>
/* All styles are now handled by the global style.css */
.no-video-message {
  color: var(--chakra-paragraph-text);
  font-style: italic;
  padding: 20px;
  text-align: center;
}
</style>