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

.rss-feed {
	display: flex;
	flex-direction: column;
	gap: 15px;
}

.rss-card {
	display: flex;
	background-color: var(--rss-card-bg);
	border-radius: 8px;
	overflow: hidden;
	box-shadow: 0 2px 4px var(--general-shadow);
	padding: 10px;
	gap: 10px;
	align-items: flex-start;
}

.rss-image, .rss-image-placeholder {
	flex-shrink: 0;
	width: 120px;
	height: 120px; /* Hauteur fixe pour cohérence */
}

.rss-image img {
	width: 100%;
	height: 100%;
	border-radius: 6px;
	object-fit: cover;
	display: block;
}

.rss-image-placeholder {
	background-color: var(--rss-image-placeholder);
	border-radius: 6px;
}

.rss-content {
	flex-grow: 1;
}

.rss-headline {
	font-size: 1em;
	font-weight: 400;
	margin: 0 0 8px 0;
}

.rss-headline a {
	color: var(--general-text);
	text-decoration: none;
	transition: color 0.2s ease;
}

.rss-headline a:hover {
	color: var(--link-hover);
	text-decoration: underline;
}

.rss-summary {
	font-size: 0.75em;
	font-weight: 400;
	color: var(--rss-summary-text);
	margin: 0 0 8px 0;
	line-height: 1.4;
}

.rss-meta {
	font-size: 0.7em;
	font-weight: 400;
	color: var(--rss-meta-text);
	display: flex;
	gap: 8px;
}

.rss-date, .rss-author {
	display: inline-block;
}

@media (max-width: 600px) {
	.rss-card {
		flex-direction: column;
		align-items: flex-start;
	}
	.rss-image, .rss-image-placeholder {
		width: 100%;
		max-height: 150px;
	}
}
</style>