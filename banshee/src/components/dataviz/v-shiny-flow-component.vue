<template>
  <div class="vue-flow-container">
    <VueFlow v-model="elements" :fit-view-on-init="true">
      <Background />
      <Controls />
    </VueFlow>
  </div>
</template>

<script setup lang="ts">
import { ref, watchEffect } from 'vue';
import { VueFlow, useVueFlow, type Elements } from '@vue-flow/core';
import { Background } from '@vue-flow/background';
import { Controls } from '@vue-flow/controls';
import '@vue-flow/core/dist/style.css';
import '@vue-flow/core/dist/theme-default.css';
import { ShinyFlow } from '../../models/dataviz/flow/ShinyFlow';

const props = defineProps<{ shinyFlow: ShinyFlow }>();

const { fitView } = useVueFlow();

const elements = ref<Elements>([]);

watchEffect(() => {
  if (props.shinyFlow) {
    const newElements: Elements = [
      ...props.shinyFlow.nodes.map(node => ({
        id: node.id,
        position: node.position,
        type: node.type || 'default',
        data: { label: node.label }, // Move label inside data
      })),
      ...props.shinyFlow.edges.map(edge => ({
        id: edge.id,
        source: edge.source,
        target: edge.target,
        label: edge.label,
        type: edge.type || 'default', // Ensure type is 'default' if null
      })),
    ];
    elements.value = newElements;
    // Use nextTick to ensure elements are rendered before fitting view
    // This might need a slight delay or a more robust way to ensure rendering
    setTimeout(() => {
      fitView();
    }, 50);
  }
});
</script>

<style scoped>
.vue-flow-container {
  width: 100%;
  height: 500px; /* Adjust height as needed */
  border: 1px solid #eee;
  margin: 10px 0;
}
</style>