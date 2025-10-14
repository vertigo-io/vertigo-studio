<template>
  <div class="shiny-flow-container">
    <div class="table-title">{{ data.title || 'Flow Editor' }}</div>
    <div ref="reteContainer" class="rete-editor"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, watch } from 'vue';
import { NodeEditor } from 'rete';
import { AreaPlugin, AreaExtensions } from 'rete-area-plugin';
import { ConnectionPlugin, Presets as ConnectionPresets } from 'rete-connection-plugin';
import { VuePlugin, Presets as VuePresets, VueArea2D } from 'rete-vue-plugin';
import { ClassicPreset as Classic } from 'rete';

import { ShinyFlow, FlowNode, FlowConnection } from '../../models/dataviz/flow/ShinyFlow'; // Adjust import path

// Define your Rete.js scheme (nodes, sockets, controls)
type Schemes = Classic.Schemes & {
  Node: MyNode;
  Connection: MyConnection;
};

class MyNode extends Classic.Node {
  width = 180;
  height = 120;
  constructor(label: string) {
    super(label);
  }
}

class MyConnection extends Classic.Connection<MyNode, MyNode> {}

const props = defineProps<{
  data: ShinyFlow
}>()

const reteContainer = ref<HTMLElement | null>(null);
let editor: NodeEditor<Schemes> | null = null;

onMounted(async () => {
  if (!reteContainer.value) return;

  editor = new NodeEditor<Schemes>();
  const area = new AreaPlugin<Schemes, VueArea2D<Schemes>>(reteContainer.value);
  const connection = new ConnectionPlugin<Schemes, AreaExtensions.AreaExtra<Schemes>>();
  const render = new VuePlugin<Schemes, VueArea2D<Schemes>>();

  AreaExtensions.selectableNodes(area, AreaExtensions.selector());

  render.addPreset(VuePresets.classic.setup());
  connection.addPreset(ConnectionPresets.classic.setup());

  editor.use(area);
  area.use(connection);
  area.use(render);

  // Add nodes to the editor
  for (const nodeData of props.data.nodes) {
    const node = new MyNode(nodeData.name);
    await editor.addNode(node);
    await area.translate(node.id, { x: nodeData.x, y: nodeData.y });
  }

  // Add connections to the editor
  for (const connData of props.data.connections) {
    const fromNode = editor.getNode(connData.from);
    const toNode = editor.getNode(connData.to);
    if (fromNode && toNode) {
      // Assuming simple input/output for now
      // You would need to define inputs/outputs on your MyNode class
      // For this example, we'll just create a connection without specific ports
      await editor.addConnection(new MyConnection(fromNode, 'output', toNode, 'input'));
    }
  }

  AreaExtensions.zoomAt(area, editor.getNodes());

  await editor.render();
});

onBeforeUnmount(() => {
  editor?.destroy();
});

watch(() => props.data, async (newData) => {
  if (editor) {
    editor.clear(); // Clear existing nodes and connections

    // Add nodes to the editor
    for (const nodeData of newData.nodes) {
      const node = new MyNode(nodeData.name);
      await editor.addNode(node);
      await area.translate(node.id, { x: nodeData.x, y: nodeData.y });
    }

    // Add connections to the editor
    for (const connData of newData.connections) {
      const fromNode = editor.getNode(connData.from);
      const toNode = editor.getNode(connData.to);
      if (fromNode && toNode) {
        await editor.addConnection(new MyConnection(fromNode, 'output', toNode, 'input'));
      }
    }
    AreaExtensions.zoomAt(area, editor.getNodes());
    await editor.render();
  }
}, { deep: true });
</script>

<style scoped>
.shiny-flow-container {
  padding: 16px;
  background-color: var(--general-surface);
  border-radius: 8px;
}

.table-title {
  font-size: 1.2em;
  font-weight: bold;
  margin-bottom: 10px;
  color: var(--chakra-title-text);
}

.rete-editor {
  width: 100%;
  height: 500px; /* Adjust as needed */
  border: 1px solid var(--assistant-accent);
  border-radius: 8px;
  background-color: var(--general-bg);
}
</style>