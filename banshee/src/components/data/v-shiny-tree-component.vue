<template>
  <div class="tree-container">
    <TreeNode :node="data.rootNode"></TreeNode>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted } from 'vue';
import TreeNode from './TreeNode.vue'; // Import the new TreeNode component
import { ShinyTree } from '../../models/ShinyTree';

// Define interfaces for better type checking
interface ShinyTreeData {
  rootNode: ShinyTree;
}

declare const lucide: any;

export default defineComponent({
  name: 'VShinyTreeComponent',
  props: {
    data: {
      type: Object as () => ShinyTreeData,
      required: true,
    },
  },
  components: {
    TreeNode, // Register the imported component
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