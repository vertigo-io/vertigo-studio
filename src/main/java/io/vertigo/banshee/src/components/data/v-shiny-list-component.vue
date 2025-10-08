<template>
  <div class="shiny-list-container">
    <h3 class="shiny-component-title">{{ data.title || 'Shiny List' }}</h3>
    <div v-html="itemsHtml"></div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

interface ShinyListItem {
  title?: string;
  type?: string;
  items?: ShinyListItem[];
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
      const listType = this.data.type || 'UNORDERED';
      const items = this.data.items || [];

      let tag: string;
      let listStyle = 'list-style-type: none; padding: 0;';
      switch (listType) {
        case 'ORDERED':
          tag = 'ol';
          listStyle = 'list-style-type: decimal; padding-left: 1.5em;';
          break;
        case 'UNORDERED':
          tag = 'ul';
          listStyle = 'list-style-type: disc; padding-left: 1.5em;';
          break;
        case 'DASHED':
          tag = 'ul';
          listStyle = 'list-style-type: none; padding: 0;'; // Custom dashed style
          break;
        default:
          tag = 'ul';
      }

      const itemsHtml = items.map(item => {
        let itemContent: string;
        if (typeof item === 'object' && item !== null && item.type && item.items) {
          // This is a naive implementation for nested lists.
          // A recursive component would be better, but for now this will do.
          itemContent = `<span style="font-weight: bold; color: #90CDF4;">${item.title}</span>` + this.generateItemsHtml(item.items, item.type);
        } else {
          itemContent = item as string;
        }

        const liStyle = listType === 'DASHED'
          ? 'margin-bottom: 0.5em; color: #CBD5E0; position: relative; padding-left: 1em;'
          : 'margin-bottom: 0.5em; color: #CBD5E0;';
        const beforeStyle = listType === 'DASHED'
          ? 'content: "- "; position: absolute; left: 0;'
          : '';

        return `<li style="${liStyle}"><span style="${beforeStyle}"></span>${itemContent}</li>`;
      }).join('');

      return `<${tag} style="${listStyle}">${itemsHtml}</${tag}>`;
    },
  },
  methods: {
    generateItemsHtml(items: (string | ShinyListItem)[], listType: string): string {
      let tag: string;
      let listStyle = 'list-style-type: none; padding: 0;';
      switch (listType) {
        case 'ORDERED':
          tag = 'ol';
          listStyle = 'list-style-type: decimal; padding-left: 1.5em;';
          break;
        case 'UNORDERED':
          tag = 'ul';
          listStyle = 'list-style-type: disc; padding-left: 1.5em;';
          break;
        case 'DASHED':
          tag = 'ul';
          listStyle = 'list-style-type: none; padding: 0;'; // Custom dashed style
          break;
        default:
          tag = 'ul';
      }

      const itemsHtml = items.map(item => {
        const liStyle = listType === 'DASHED'
          ? 'margin-bottom: 0.5em; color: #CBD5E0; position: relative; padding-left: 1em;'
          : 'margin-bottom: 0.5em; color: #CBD5E0;';
        const beforeStyle = listType === 'DASHED'
          ? 'content: "- "; position: absolute; left: 0;'
          : '';
        return `<li style="${liStyle}"><span style="${beforeStyle}"></span>${item}</li>`;
      }).join('');
      return `<${tag} style="${listStyle}">${itemsHtml}</${tag}>`;
    },
  },
});
</script>

<style scoped>
.shiny-list-container {
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

.shiny-list-container ul, .shiny-list-container ol {
  margin-top: 10px;
}
</style>