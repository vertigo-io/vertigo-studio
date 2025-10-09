<template>
  <div class="list-container">
    <div class="table-title">{{ data.title || 'List' }}</div>
    <div v-html="itemsHtml"></div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { ShinyList } from '../../models/data/list/ShinyList';
import { ShinyListType } from '../../models/data/list/ShinyListType';

export default defineComponent({
  name: 'VShinyListComponent',
  props: {
    data: {
      type: Object as () => ShinyList,
      required: true,
    },
  },
  computed: {
    itemsHtml(): string {
      return this.generateItemsHtml(this.data.items, this.data.listType);
    },
  },
  methods: {
    generateItemsHtml(items: (string | ShinyList)[],
 listType: ShinyListType): string {
      const tag = listType === ShinyListType.ORDERED ? 'ol' : 'ul';
      const listClass = listType === ShinyListType.DASHED ? 'class="dashed-list"' : '';

      const itemsHtml = items.map(item => {
        let itemContent: string;
        if (typeof item === 'object' && item !== null && 'items' in item) {
          // This is a nested ShinyList
          const nestedList = item as ShinyList;
          const title = nestedList.title ? `<span class="nested-title">${nestedList.title}</span>` : '';
          itemContent = title + this.generateItemsHtml(nestedList.items, nestedList.listType);
        } else {
          // Simple string item
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