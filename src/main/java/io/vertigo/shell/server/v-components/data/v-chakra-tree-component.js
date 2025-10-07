Vue.component('v-chakra-tree-component', {
    props: ['data'],
    template: `
    <div class="chakra-tree">
        <node :node="data.rootNode"></node>
    </div>
    `
});

Vue.component('node', {
    props: ['node'],
    template: `
    <div class="chakra-tree-node">
        <i v-if="node.icon && node.icon !== 'NONE'" :data-lucide="node.icon.toLowerCase()" class="chakra-tree-node-icon"></i>
        <span class="chakra-tree-node-label">{{ node.label }}</span>
        <div v-if="node.children && node.children.length > 0" class="chakra-tree-node-children">
            <node v-for="(child, index) in node.children" :key="index" :node="child"></node>
        </div>
    </div>
    `
});