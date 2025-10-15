<template>
  <div class="workflow-canvas">
    <VueFlow v-model="elements" :fit-view-on-init="true">
      <Background variant="dots" :gap="20" :size="1" />
      <Controls />
    </VueFlow>
  </div>
</template>

<script setup lang="ts">
import { ref, watchEffect } from 'vue'
import { VueFlow, useVueFlow, type Elements } from '@vue-flow/core'
import { Background } from '@vue-flow/background'
import { Controls } from '@vue-flow/controls'
import '@vue-flow/core/dist/style.css'
import '@vue-flow/core/dist/theme-default.css'
import { ShinyFlow } from '../../models/dataviz/flow/ShinyFlow'

const props = defineProps<{ data: ShinyFlow }>()
const { fitView } = useVueFlow()
const elements = ref<Elements>([])

watchEffect(() => {
  if (props.data) {
    const newElements: Elements = [
      ...props.data.nodes.map(node => ({
        id: node.id,
        position: node.position,
        type: node.type || 'default',
        data: { label: node.label },
        style: {
          background: '#ffffff',
          border: '1px solid #d1d5db',
          borderRadius: '12px',
          padding: '10px 16px',
          boxShadow: '0 2px 6px rgba(0, 0, 0, 0.05)',
          fontWeight: 500,
          color: '#111827',
          transition: 'all 0.2s ease-in-out',
        },
      })),
      ...props.data.edges.map(edge => ({
        id: edge.id,
        source: edge.source,
        target: edge.target,
        label: edge.label,
        type: edge.type || 'smoothstep',
        animated: true,
        style: { stroke: '#6366f1', strokeWidth: 2 },
        labelStyle: { fill: '#4f46e5', fontWeight: 500, fontSize: '12px' },
      })),
    ]
    elements.value = newElements
    setTimeout(() => fitView(), 50)
  }
})
</script>

<style scoped>
.workflow-canvas {
  width: 100%;
  height: 600px;
  background: #f9fafb;
  border-radius: 16px;
  border: 1px solid #e5e7eb;
  overflow: hidden;
  position: relative;
  transition: box-shadow 0.2s ease-in-out;
}

.workflow-canvas:hover {
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
}

/* Personnalisation des contrôles */
.vue-flow__controls {
  background: #ffffff !important;
  border: 1px solid #e5e7eb !important;
  border-radius: 10px !important;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05) !important;
}

.vue-flow__controls button {
  background: none !important;
  border: none !important;
  color: #4b5563 !important;
  transition: all 0.2s ease-in-out;
}

.vue-flow__controls button:hover {
  background: #eef2ff !important;
  color: #4338ca !important;
}

/* Animation légère sur les nœuds */
.vue-flow__node:hover {
  transform: scale(1.03);
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.08);
  border-color: #6366f1;
}

/* Bordures et connecteurs Make.com-like */
.vue-flow__edge-path {
  stroke-linecap: round;
}
</style>
