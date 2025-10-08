<template>
  <div class="shiny-text-path-container">
    <h3 class="shiny-component-title">{{ data.title || 'Shiny Text Path' }}</h3>
    <div class="shiny-text-path" v-html="pathHtml"></div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'VShinyTextPathComponent',
  props: {
    data: {
      type: Object,
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
.shiny-text-path-container {
  background-color: #1A202C;
  padding: 15px;
  border-radius: 8px;
  color: #CBD5E0;
}

.shiny-component-title {
  color: #E2E8F0;
  margin-bottom: 10px;
}

.shiny-text-path {
  font-family: monospace;
  font-size: 1.1em;
  display: flex;
  align-items: center;
  flex-wrap: wrap;
}

.path-node {
  color: #90CDF4; /* Light blue */
}

.path-root {
  font-weight: bold;
  color: #4FD1C5; /* Teal */
}

.path-leaf {
  font-style: italic;
  color: #F6AD55; /* Orange */
}

.path-separator {
  margin: 0 5px;
  color: #A0AEC0; /* Gray */
}
</style>