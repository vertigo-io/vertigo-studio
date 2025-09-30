Vue.component('v-chakra-tree-component', {
    props: ['data'],
    template: `
    <div class="chakra-tree" style="color: #CBD5E0; background-color: #1A202C; padding: 10px; border-radius: 8px;">
        <node :node="data.rootNode"></node>
    </div>
    `
});

Vue.component('node', {
    props: ['node'],
    template: `
    <div style="margin-left: 15px;">
        <span style="font-weight: bold; color: #90CDF4;">{{ node.label }}</span>
        <div v-if="node.children && node.children.length > 0" style="margin-left: 10px;">
            <node v-for="(child, index) in node.children" :key="index" :node="child"></node>
        </div>
    </div>
    `
});