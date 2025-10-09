<template>
  <div class="tree-node">
    <i v-if="node.icon && node.icon.toLowerCase() !== 'none'" :data-lucide="node.icon.toLowerCase()" class="tree-icon"></i>
    <span class="node-label">{{ node.label }}</span>
    <div v-if="node.children && node.children.length > 0" class="tree-nodes">
      <TreeNode v-for="(child, index) in node.children" :key="index" :node="child" v-if="child"></TreeNode>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted } from 'vue';
import { ShinyTree } from '../../models/ShinyTree';

interface ShinyTreeNode {
  label: string;
  icon?: string;
  children?: ShinyTree[]; // Use ShinyTree for children
}

declare const lucide: any;

export default defineComponent({
  name: 'TreeNode',
  props: {
    node: {
      type: Object as () => ShinyTree,
      required: true,
    },
  },
  setup(props) {
    onMounted(() => {
      console.log('TreeNode mounted, props.node:', props.node);
      if (props.node.icon && props.node.icon.toLowerCase() !== 'none' && typeof lucide !== 'undefined') {
        lucide.createIcons();
      }
    });
    return { node: props.node }; // Expose node to template
  },
});
</script>

<style scoped>
/* All styles are now handled by the global style.css */
</style>
