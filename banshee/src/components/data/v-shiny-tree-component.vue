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
  background-color: var(--general-surface);
  border-radius: 8px;
}

.table-title {
  font-size: 1.2em;
  font-weight: bold;
  margin-bottom: 10px;
  color: var(--chakra-title-text);
}
</style>