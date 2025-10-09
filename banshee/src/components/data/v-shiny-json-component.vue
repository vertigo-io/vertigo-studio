<template>
  <div class="shiny-json-container">
    <h3 class="shiny-component-title">{{ data.title || 'Shiny JSON' }}</h3>
    <div class="json-display" v-html="highlightedJson"></div>
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
      return json.replace(/("(\u[a-zA-Z0-9]{4}|\\.[^u]|[^\\"])*"(?:\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function(match) {
        let escapedMatch = match.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
        if (/^"/.test(match)) {
          return /:$/.test(match) ? `<span class="json-key">${escapedMatch}</span>` : `<span class="json-string">${escapedMatch}</span>`;
        } else if (/true|false/.test(match)) {
          return `<span class="json-boolean">${escapedMatch}</span>`;
        } else if (/null/.test(match)) {
          return `<span class="json-null">${escapedMatch}</span>`;
        } else {
          return `<span class="json-number">${escapedMatch}</span>`;
        }
      });
    },
  },
});
</script>

<style scoped>
.shiny-json-container {
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

.json-display {
  font-family: 'Courier New', Courier, monospace;
  white-space: pre-wrap;
  word-wrap: break-word;
  text-align: left;
}

.json-key {
  color: #90CDF4; /* Light blue */
}

.json-string {
  color: #9AE6B4; /* Light green */
}

.json-boolean {
  color: #F6AD55; /* Orange */
}

.json-null {
  color: #FC8181; /* Red */
}

.json-number {
  color: #B794F4; /* Purple */
}
</style>
