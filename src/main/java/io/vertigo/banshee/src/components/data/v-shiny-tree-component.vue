<template>
  <div class="shiny-tree-container">
    <node :node="data.rootNode"></node>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

// Define interfaces for better type checking
interface ShinyTreeNode {
  label: string;
  icon?: string; // Assuming icon is a string like 'FOLDER_OPEN', 'FILE', or 'NONE'
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
      name: 'TreeNode', // Give it a name for debugging
      props: {
        node: {
          type: Object as () => ShinyTreeNode,
          required: true,
        },
      },
      template: `
        <div class="shiny-tree-node">
          <i v-if="node.icon && node.icon !== 'NONE'" :data-lucide="node.icon.toLowerCase()" class="shiny-tree-node-icon"></i>
          <span class="shiny-tree-node-label">{{ node.label }}</span>
          <div v-if="node.children && node.children.length > 0" class="shiny-tree-node-children">
            <TreeNode v-for="(child, index) in node.children" :key="index" :node="child"></TreeNode>
          </div>
        </div>
      `,
      mounted() {
        // Initialize Lucide icons for this node
        if (this.node.icon && this.node.icon !== 'NONE' && typeof lucide !== 'undefined') {
          lucide.createIcons();
        }
      }
    }),
  },
  mounted() {
    // Initialize Lucide icons for the root node
    if (this.data.rootNode.icon && this.data.rootNode.icon !== 'NONE' && typeof lucide !== 'undefined') {
      lucide.createIcons();
    }
  }
});
</script>

<style scoped>
.shiny-tree-container {
  background-color: #1A202C;
  padding: 15px;
  border-radius: 8px;
  color: #CBD5E0;
  text-align: left;
}

.shiny-tree-node {
  margin-left: 15px;
  line-height: 1.5;
}

.shiny-tree-node-icon {
  margin-right: 5px;
  color: #90CDF4; /* Example color for icons */
}

.shiny-tree-node-label {
  font-weight: bold;
  color: #E2E8F0;
}

.shiny-tree-node-children {
  border-left: 1px solid #4A5568;
  margin-left: 8px;
  padding-left: 8px;
}
</style>