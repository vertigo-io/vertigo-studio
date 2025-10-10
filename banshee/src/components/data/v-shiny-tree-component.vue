<template>
  <div class="tree-container">
    <TreeNode :node="data.rootNode"></TreeNode>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue';
import TreeNode from './TreeNode.vue'; // Import the new TreeNode component
import { ShinyTree } from '../../models/data/tree/ShinyTree';

declare const lucide: any;

const props = defineProps<{
  data: { rootNode: ShinyTree }
}>()

onMounted(() => {
  if (props.data.rootNode.icon && props.data.rootNode.icon.toLowerCase() !== 'none' && typeof lucide !== 'undefined') {
    lucide.createIcons();
  }
});
</script>

<style scoped>
/* All styles are now handled by the global style.css */
</style>