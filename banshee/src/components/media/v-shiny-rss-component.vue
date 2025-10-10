<template>
  <div class="rss-feed">
    <div class="table-title">{{ data.title || 'RSS Feed' }}</div>
    <div v-if="!data.items || data.items.length === 0">No items in the feed.</div>
    <div v-for="(item, index) in data.items" :key="index" class="rss-card">
      <div v-if="item.image" class="rss-image">
        <img :src="item.image" :alt="item.title" loading="lazy" onerror="this.style.display='none';">
      </div>
      <div v-else class="rss-image-placeholder"></div>
      <div class="rss-content">
        <h3 class="rss-headline">
          <a :href="item.link" target="_blank" rel="noopener noreferrer">{{ item.title }}</a>
        </h3>
        <p class="rss-summary">{{ summary(item.description) }}</p>
        <p class="rss-meta">
          <span v-if="item.author">By {{ item.author }}</span>
          <span v-if="item.pubDate"> - {{ item.pubDate }}</span>
        </p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ShinyRss } from '../../models/media/rss/ShinyRss';

const props = defineProps<{
  data: ShinyRss
}>()

const summary = (description: string): string => {
  return description && description.length > 150 ? description.substring(0, 150) + '...' : description;
};
</script>

<style scoped>
/* All styles are now handled by the global style.css */
</style>