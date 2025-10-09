<template>
  <div class="json-container">
    <div class="table-title">{{ data.title || 'JSON Viewer' }}</div>
    <pre v-html="highlightedJson"></pre>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'VShinyJsonComponent',
  props: {
    data: {
      type: Object,
      required: true,
    },
  },
  computed: {
    highlightedJson(): string {
      let json = this.data.json || {};
      if (typeof json !== 'string') {
        json = JSON.stringify(json, null, 2);
      }
      // Basic HTML escaping
      const escapedJson = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
      
      // Regex to find JSON parts and wrap them in spans with corresponding classes
      return escapedJson.replace(/("(\u[a-zA-Z0-9]{4}|\\[^u]|[^\\\"])*"(\s*)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function(match) {
        if (/^"/.test(match)) {
          return /:$/.test(match) ? `<span class="json-key">${match}</span>` : `<span class="json-string">${match}</span>`;
        } else if (/true|false/.test(match)) {
          return `<span class="json-boolean">${match}</span>`;
        } else if (/null/.test(match)) {
          return `<span class="json-null">${match}</span>`;
        } else {
          return `<span class="json-number">${match}</span>`;
        }
      });
    },
  },
});
</script>

<style scoped>
/* All styles are now handled by the global style.css */
</style>
