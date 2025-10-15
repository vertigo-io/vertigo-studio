<template>
  <div style="height: 500px;">
    <VueFlow :nodes="nodes" :edges="edges" fit-view-on-init>
      <template #node-default="props">
        <div :style="props.data.style">
          {{ props.data.label }}
        </div>
      </template>
    </VueFlow>
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';
import { VueFlow } from '@vue-flow/core';
import { ShinyFlow, ShinyFlowNode, ShinyFlowEdge } from '../../models/dataviz/flow/ShinyFlow';

const props = defineProps<{
  data: ShinyFlow
}>();

const nodes = ref<ShinyFlowNode[]>([]);
const edges = ref<ShinyFlowEdge[]>([]);

watch(() => props.data, (newData) => {
  if (newData) {
    nodes.value = newData.nodes.map(node => ({
        ...node,
        data: { label: node.label, style: node.style }
    }));
    edges.value = newData.edges;
  }
}, { immediate: true, deep: true });

</script>

<style>
@import '@vue-flow/core/dist/style.css';
@import '@vue-flow/core/dist/theme-default.css';
</style>
