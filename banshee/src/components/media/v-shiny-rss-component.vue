<template>
  <div class="shiny-rss-feed">
    <h3 class="shiny-component-title">{{ data.title || 'Shiny RSS Feed' }}</h3>
    <div v-if="!data.items || data.items.length === 0">No items in the feed.</div>
    <div v-for="(item, index) in data.items" :key="index" class="shiny-rss-card">
      <div v-if="item.image" class="shiny-rss-image">
        <img :src="item.image" :alt="item.title" loading="lazy" onerror="this.style.display='none';">
      </div>
      <div v-else class="shiny-rss-image-placeholder"></div>
      <div class="shiny-rss-content">
        <h3 class="shiny-rss-headline">
          <a :href="item.link" target="_blank" rel="noopener noreferrer">{{ item.title }}</a>
        </h3>
        <p class="shiny-rss-summary">{{ summary(item.description) }}</p>
        <p class="shiny-rss-meta">
          <span v-if="item.author">By {{ item.author }}</span>
          <span v-if="item.pubDate"> - {{ item.pubDate }}</span>
        </p>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'VShinyRssComponent',
  props: {
    data: {
      type: Object,
      required: true,
    },
  },
  methods: {
    summary(description: string): string {
      return description && description.length > 150 ? description.substring(0, 150) + '...' : description;
    },
  },
});
</script>

<style scoped>
.shiny-rss-feed {
  background-color: #1A202C;
  padding: 15px;
  border-radius: 8px;
  color: #CBD5E0;
}

.shiny-component-title {
  color: #E2E8F0;
  margin-bottom: 15px;
  text-align: center;
}

.shiny-rss-card {
  display: flex;
  margin-bottom: 20px;
  background-color: #2D3748;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.shiny-rss-image {
  width: 150px;
  height: 100px;
  flex-shrink: 0;
}

.shiny-rss-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.shiny-rss-image-placeholder {
  width: 150px;
  height: 100px;
  flex-shrink: 0;
  background-color: #4A5568;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #CBD5E0;
  font-size: 0.8em;
  text-align: center;
}

.shiny-rss-content {
  padding: 15px;
  flex-grow: 1;
}

.shiny-rss-headline {
  font-size: 1.2em;
  margin-top: 0;
  margin-bottom: 5px;
}

.shiny-rss-headline a {
  color: #90CDF4;
  text-decoration: none;
}

.shiny-rss-headline a:hover {
  text-decoration: underline;
}

.shiny-rss-summary {
  font-size: 0.9em;
  color: #A0AEC0;
  margin-bottom: 10px;
}

.shiny-rss-meta {
  font-size: 0.8em;
  color: #718096;
}
</style>