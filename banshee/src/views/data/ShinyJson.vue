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
  
  // Convertir en string d'abord
  let jsonString: string;
  if (typeof json === 'string') {
    jsonString = json;
  } else {
    jsonString = JSON.stringify(json, null, 2);
  }
  
  // Maintenant on peut utiliser .replace() sur jsonString
  const escapedJson = jsonString
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;');
  
  // Coloration syntaxique
  return escapedJson.replace(
    /("(?:\\.|[^"\\])*")(\s*:\s*)?|(\b(?:true|false|null)\b)|(-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g,
    (match, quotedString, colon, bool, num) => {
      if (quotedString) {
        if (colon) {
          return `<span class="json-key">${quotedString}</span>${colon}`;
        }
        return `<span class="json-string">${quotedString}</span>`;
      } else if (bool) {
        return `<span class="json-boolean">${bool}</span>`;
      } else if (num) {
        return `<span class="json-number">${num}</span>`;
      }
      return match;
    }
  );
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
