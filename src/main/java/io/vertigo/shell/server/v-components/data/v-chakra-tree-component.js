Vue.component('v-chakra-tree-component', {
  props: ['data'],
  template: `
    <div class="chakra-tree">
      <node :node="data"></node>
    </div>
  `
});

Vue.component('node', {
  props: ['node'],
  template: `
    <div class="chakra-tree-node">
      <i v-if="node.leaf" data-lucide="file" class="chakra-tree-node-icon"></i>
      <i v-else data-lucide="folder" class="chakra-tree-node-icon"></i>
      <span class="chakra-tree-node-label">{{ node.label }}</span>
      <div v-if="node.children && node.children.length > 0" class="chakra-tree-node-children">
        <node v-for="(child, index) in node.children" :key="index" :node="child"></node>
      </div>
    </div>
  `
});