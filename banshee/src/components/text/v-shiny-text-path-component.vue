<template>
  <div class="chart-container">
    <div class="table-title">{{ data.title || 'Text Path' }}</div>
    <div class="shiny-text-path" v-html="pathHtml"></div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { ShinyTextPath } from '../../models/text/textpath/ShinyTextPath';

const props = defineProps<{
  data: ShinyTextPath
}>()

const pathHtml = computed((): string => {
  const parts = (props.data.path || '').split(props.data.separator || '/');
  let html = '';
  parts.forEach((part: string, index: number) => {
    let partClass = 'path-node';
    if (index === 0) {
      partClass = 'path-root';
    } else if (index === parts.length - 1) {
      partClass = 'path-leaf';
    }
    html += `<span class="${partClass}">${part}</span>`;
    if (index < parts.length - 1) {
      html += `<span class="path-separator">${props.data.separator || '/'}</span>`;
    }
  });
  return html;
});
</script>

<style scoped>
/* All styles are now handled by the global style.css */
.shiny-text-path {
  font-family: monospace;
  font-size: 1.1em;
  display: flex;
  align-items: center;
  flex-wrap: wrap;
}
</style>