<template>
  <div class="tree-node">
    <i v-if="node.icon && node.icon.toLowerCase() !== 'none'" :data-lucide="node.icon.toLowerCase()" class="tree-icon"></i>
    <span class="node-label">{{ node.label }}</span>
    <div v-if="node.children && node.children.length > 0" class="tree-nodes">
      <TreeNode v-for="(child, index) in node.children" :key="index" :node="child" v-if="child"></TreeNode>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue';
import { ShinyTree } from '../../models/data/tree/ShinyTree';

declare const lucide: any;

const props = defineProps<{
  node: ShinyTree
}>()

onMounted(() => {
  console.log('TreeNode mounted, props.node:', props.node);
  if (props.node.icon && props.node.icon.toLowerCase() !== 'none' && typeof lucide !== 'undefined') {
    lucide.createIcons();
  }
});
</script>

<style scoped>
/* All styles are now handled by the global style.css */
</style>
