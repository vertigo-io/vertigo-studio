<template>
  <div class="json-container">
    <div class="table-title">{{ data.title || 'JSON Viewer' }}</div>
    <pre v-html="highlightedJson"></pre>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { ShinyJson } from '../../models/data/json/ShinyJson';

const props = defineProps<{
  data: ShinyJson
}>()

const highlightedJson = computed((): string => {
  let json = props.data.json || {};
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
});
</script>

<style scoped>

.json-container {
	background-color: var(--json-bg);
	border-radius: 8px;
	padding: 16px;
	margin-top: 10px;
	border: 1px solid var(--assistant-bg);
	font-family: 'Fira Code', 'Courier New', monospace;
	font-size: 14px;
	line-height: 1.5;
	overflow-x: auto;
	white-space: pre;
}

.json-container::-webkit-scrollbar {
	width: 10px;
	height: 10px;
}

.json-container::-webkit-scrollbar-track {
	background: var(--general-bg);
	border-radius: 10px;
}

.json-container::-webkit-scrollbar-thumb {
	background: var(--assistant-bg);
	border-radius: 10px;
	border: 2px solid var(--json-bg);
}

.json-container::-webkit-scrollbar-thumb:hover {
	background: var(--assistant-accent);
}

.json-container::-webkit-scrollbar-corner {
	background: var(--json-bg);
}

.json-key {
	color: var(--json-key);
}

.json-string {
	color: var(--json-string);
}

.json-number {
	color: var(--json-number);
}

.json-boolean {
	color: var(--json-boolean);
}

.json-null {
	color: var(--json-null);
}
</style>
