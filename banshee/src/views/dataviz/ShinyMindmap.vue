<template>
  <div class="shiny-mindmap-container">
    <div class="table-title">{{ data.title || 'Mind Map' }}</div>
    <div :id="mindmapId" class="mindmap-editor"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, watch } from 'vue';
import '../../assets/jsmind.css';
import jsMind from 'jsmind';

import { ShinyMindMap } from '../../models/dataviz/mindmap/ShinyMindMap'; 
import {  ShinyMindMapNode } from '../../models/dataviz/mindmap/ShinyMindMapNode';



const props = defineProps<{
  data: ShinyMindMap
}>()

const mindmapId = ref(`mindmap-${Math.random().toString(36).substr(2, 9)}`);
let jm: any = null; // jsMind instance

const formatMindMapData = (node: ShinyMindMapNode): any => {
  const formattedNode: any = {
    id: node.id,
    topic: node.topic,
  };
  if (node.background) formattedNode.background = node.background;
  if (node.foreground) formattedNode.foreground = node.foreground;
  if (node.direction) formattedNode.direction = node.direction;
  formattedNode.expanded = node.expanded ; 

  if (node.children && node.children.length > 0) {
    formattedNode.children = node.children.map(formatMindMapData);
  }
  return formattedNode;
};

const initMindMap = () => {
  if (!jsMind) {
    console.error('jsMind library not loaded.');
    return;
  }

  const mind = {
    meta: {
      name: props.data.title || 'Mind Map',
      author: 'Banshee',
      version: '1.0',
    },
    format: 'node_tree',
    data: formatMindMapData(props.data.rootNode),
  };

  const options = {
    container: mindmapId.value,
    editable: false,
    theme: 'primary', // You can define custom themes or use built-in ones
  };
  jm = new jsMind(options);
  jm.show(mind);
};

onMounted(() => {
  initMindMap();
});

onBeforeUnmount(() => {
  // jsmind doesn't have a direct destroy method, but we can clear the container
  if (jm) {
    jm.disable_edit(); // Disable editing
    // jm.view.get_container().innerHTML = ''; // Clear the DOM element if needed
  }
});

watch(() => props.data, (newData) => {
  if (jm && newData.rootNode) {
    const mind = {
        meta: {
            name: newData.title || 'Mind Map',
            author: 'Banshee',
            version: '1.0',
        },
        format: 'node_tree',
        data: formatMindMapData(newData.rootNode),
    };
    jm.show(mind);
  }
}, { deep: true });
</script>

<style scoped>
.shiny-mindmap-container {
  padding: 16px;
  background-color: var(--general-surface);
  border-radius: 8px;
}

.table-title {
  font-size: 1.2em;
  font-weight: bold;
  margin-bottom: 10px;
  color: var(--chakra-title-text);
}

.mindmap-editor {
  width: 100%;
  height: 500px; /* Adjust as needed */
  border: 1px solid var(--assistant-accent);
  border-radius: 8px;
  background-color: var(--general-bg);
}
</style>