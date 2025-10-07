Vue.component('v-chakra-tree-component', {
  props: ['data'],
  template: `
    <div class="chakra-tree">
      <div class="chakra-tree-node">
        <i v-if="data.leaf" data-lucide="file" class="chakra-tree-node-icon"></i>
        <i v-else data-lucide="folder" class="chakra-tree-node-icon"></i>
        <span class="chakra-tree-node-label">{{ data.label }}</span>
        <div v-if="data.children && data.children.length > 0" class="chakra-tree-node-children">
          <v-chakra-tree-component v-for="(child, index) in data.children" :key="index" :data="child"></v-chakra-tree-component>
        </div>
      </div>
    </div>
  `
});