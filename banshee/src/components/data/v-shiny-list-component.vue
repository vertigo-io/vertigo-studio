<template>
  <div class="list-container">
    <div class="table-title">{{ data.title || 'List' }}</div>
    <div v-html="itemsHtml"></div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

interface ShinyListItem {
  title?: string;
  type?: 'ORDERED' | 'UNORDERED' | 'DASHED';
  items?: (string | ShinyListItem)[];
}

interface ShinyListData {
  title?: string;
  type?: 'ORDERED' | 'UNORDERED' | 'DASHED';
  items: (string | ShinyListItem)[];
}

export default defineComponent({
  name: 'VShinyListComponent',
  props: {
    data: {
      type: Object as () => ShinyListData,
      required: true,
    },
  },
  computed: {
    itemsHtml(): string {
      return this.generateItemsHtml(this.data.items, this.data.type || 'UNORDERED');
    },
  },
  methods: {
    generateItemsHtml(items: (string | ShinyListItem)[], listType: string): string {
      const tag = listType === 'ORDERED' ? 'ol' : 'ul';
      const listClass = listType === 'DASHED' ? 'class="dashed-list"' : '';

      const itemsHtml = items.map(item => {
        let itemContent: string;
        if (typeof item === 'object' && item !== null && item.items) {
          // Nested list
          const title = item.title ? `<span class="nested-title">${item.title}</span>` : '';
          itemContent = title + this.generateItemsHtml(item.items, item.type || 'UNORDERED');
        } else {
          // Simple item
          itemContent = item as string;
        }
        return `<li>${itemContent}</li>`;
      }).join('');

      return `<${tag} ${listClass}>${itemsHtml}</${tag}>`;
    },
  },
});
</script>

<style scoped>
/* All styles are now handled by the global style.css */
</style>