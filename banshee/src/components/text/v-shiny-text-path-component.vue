<template>
  <div class="chart-container">
    <div class="table-title">{{ data.title || 'Text Path' }}</div>
    <div class="shiny-text-path" v-html="pathHtml"></div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { ShinyTextPath } from '../../models/text/textpath/ShinyTextPath';

export default defineComponent({
  name: 'VShinyTextPathComponent',
  props: {
    data: {
      type: Object as () => ShinyTextPath,
      required: true,
    },
  },
  computed: {
    pathHtml(): string {
      const parts = (this.data.path || '').split(this.data.separator || '/');
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
          html += `<span class="path-separator">${this.data.separator || '/'}</span>`;
        }
      });
      return html;
    },
  },
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