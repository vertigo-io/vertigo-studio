<template>
  <div class="list-container">
    <div class="table-title">{{ data.title || 'List' }}</div>
    <div v-html="itemsHtml"></div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { ShinyList } from '../../models/data/list/ShinyList';
import { ShinyListType } from '../../models/data/list/ShinyListType';

const props = defineProps<{
  data: ShinyList
}>()

const generateItemsHtml = (items: (string | ShinyList)[], listType: ShinyListType): string => {
  const tag = listType === ShinyListType.ORDERED ? 'ol' : 'ul';
  const listClass = listType === ShinyListType.DASHED ? 'class="dashed-list"' : '';

  const itemsHtml = items.map(item => {
    let itemContent: string;
    if (typeof item === 'object' && item !== null && 'items' in item) {
      // This is a nested ShinyList
      const nestedList = item as ShinyList;
      const title = nestedList.title ? `<span class="nested-title">${nestedList.title}</span>` : '';
      itemContent = title + generateItemsHtml(nestedList.items, nestedList.listType);
    } else {
      // Simple string item
      itemContent = item as string;
    }
    return `<li>${itemContent}</li>`;
  }).join('');

  return `<${tag} ${listClass}>${itemsHtml}</${tag}>`;
};

const itemsHtml = computed((): string => {
  return generateItemsHtml(props.data.items, props.data.listType);
});
</script>

<style scoped>
/* All styles are now handled by the global style.css */
</style>