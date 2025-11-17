<template>
  <div class="tree-container">
    <div class="table-title">{{ data.label || 'Tree View' }}</div>
    <v-treeview
      v-if="data"
      :items="[data]"
      item-key="label"
      item-title="label"
      open-on-click
      activatable
      return-object
    >
      <template v-slot:prepend="{ item, toggle, open }">
        <v-icon v-if="item.children && item.children.length > 0" @click="toggle">
          {{ open ? 'mdi-folder-open' : 'mdi-folder' }}
        </v-icon>
        <v-icon v-else>
          {{ item.icon || 'mdi-file-document-outline' }}
        </v-icon>
      </template>
    </v-treeview>
    <div v-else class="no-data-message">No tree data available.</div>
  </div>
</template>

<script setup lang="ts">
import { ShinyTree } from '../../models/data/tree/ShinyTree';

const props = defineProps<{
  data: ShinyTree
}>()
</script>

<style scoped>
/* Add any specific styles for the tree container if needed */
.tree-container {
  padding: 16px; 
  background: var(--general-bg);
  background-color: var(--general-surface);
  border-radius: 8px;
  box-shadow: 0 4px 16px var(--general-shadow);
	font-family: 'Inter', 'Segoe UI', system-ui, sans-serif;
	color: var(--general-text);
}

.table-title {
  font-size: 1.2em;
  font-weight: bold;
  margin-bottom: 10px;
  color: var(--chakra-title-text);
}

.tree-nodes {
	list-style: none;
	padding-left: 20px;
	margin: 0;
}

.tree-node {
	display: flex;
	align-items: center;
	padding: 5px 0;
	cursor: pointer;
}

.tree-node .node-label {
	margin-left: 5px;
	font-weight: 500;
}

.tree-icon {
	width: 16px;
	height: 16px;
	stroke: var(--icon-stroke);
	stroke-width: 2;
	flex-shrink: 0;
	margin-right: 5px;
}

.tree-node ul {
	margin-left: 15px;
	border-left: 1px solid var(--tree-border);
	padding-left: 10px;
}

</style>