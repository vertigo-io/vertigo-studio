<template>
  <div class="tree-container">
    <node :node="data.rootNode"></node>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

// Define interfaces for better type checking
interface ShinyTreeNode {
  label: string;
  icon?: string; // Assuming icon is a string like 'folder-open', 'file', or 'none'
  children?: ShinyTreeNode[];
}

interface ShinyTreeData {
  rootNode: ShinyTreeNode;
}

declare const lucide: any; // Declare lucide to avoid TypeScript errors

export default defineComponent({
  name: 'VShinyTreeComponent',
  props: {
    data: {
      type: Object as () => ShinyTreeData,
      required: true,
    },
  },
  components: {
    // Define the recursive 'node' component locally
    node: defineComponent({
      name: 'TreeNode',
      props: {
        node: {
          type: Object as () => ShinyTreeNode,
          required: true,
        },
      },
      template: `
        <div class="tree-node">
          <i v-if="node.icon && node.icon.toLowerCase() !== 'none'" :data-lucide="node.icon.toLowerCase()" class="tree-icon"></i>
          <span class="node-label">{{ node.label }}</span>
          <div v-if="node.children && node.children.length > 0" class="tree-nodes">
            <TreeNode v-for="(child, index) in node.children" :key="index" :node="child"></TreeNode>
          </div>
        </div>
      `,
      mounted() {
        if (this.node.icon && this.node.icon.toLowerCase() !== 'none' && typeof lucide !== 'undefined') {
          lucide.createIcons();
        }
      }
    }),
  },
  mounted() {
    if (this.data.rootNode.icon && this.data.rootNode.icon.toLowerCase() !== 'none' && typeof lucide !== 'undefined') {
      lucide.createIcons();
    }
  }
});
</script>

<style scoped>
/* All styles are now handled by the global style.css */
</style>