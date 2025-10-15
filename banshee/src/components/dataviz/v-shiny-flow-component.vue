<template>
  <div class="workflow-dark">
    <VueFlow v-model="elements" :fit-view-on-init="true">
      <Background variant="dots" :gap="20" :size="1" color="#2d3748" />
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

// Palette Make-like sombre avec couleurs vives par type de nœud
const nodeColors = [
  'linear-gradient(135deg, #6366f1, #8b5cf6)',
  'linear-gradient(135deg, #10b981, #34d399)',
  'linear-gradient(135deg, #f59e0b, #fbbf24)',
  'linear-gradient(135deg, #ef4444, #f87171)',
  'linear-gradient(135deg, #3b82f6, #60a5fa)',
]

watchEffect(() => {
  if (props.data) {
    const newElements: Elements = [
      ...props.data.nodes.map((node, i) => ({
        id: node.id,
        position: node.position,
        type: node.type || 'default',
        data: { label: node.label },
        style: {
          background: nodeColors[i % nodeColors.length],
          border: '2px solid #1f2937',
          borderRadius: '14px',
          padding: '12px 20px',
          color: '#fff',
          fontWeight: 600,
          boxShadow: '0 4px 12px rgba(0, 0, 0, 0.4)',
          transition: 'transform 0.2s ease, box-shadow 0.2s ease',
        },
      })),
      ...props.data.edges.map(edge => ({
        id: edge.id,
        source: edge.source,
        target: edge.target,
        label: edge.label,
        type: edge.type || 'smoothstep',
        animated: true,
        style: { stroke: '#818cf8', strokeWidth: 2 },
        labelStyle: { fill: '#a5b4fc', fontWeight: 500, fontSize: '12px' },
      })),
    ]
    elements.value = newElements
    setTimeout(() => fitView(), 80)
  }
})
</script>

<style scoped>
.workflow-dark {
  width: 100%;
  height: 600px;
  background: radial-gradient(circle at 50% 50%, #111827, #0f172a);
  border-radius: 16px;
  border: 1px solid #1e293b;
  position: relative;
  overflow: hidden;
  color: #e5e7eb;
}

/* Contrôles Make.com-like */
.vue-flow__controls {
  background: #1e293b !important;
  border: 1px solid #334155 !important;
  border-radius: 10px !important;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.4) !important;
}

.vue-flow__controls button {
  background: none !important;
  border: none !important;
  color: #cbd5e1 !important;
  transition: all 0.2s ease;
}

.vue-flow__controls button:hover {
  background: #334155 !important;
  color: #facc15 !important;
}

/* Animation sur les edges (clignotement Make.com style) */
.vue-flow__edge-path {
  stroke-linecap: round;
  animation: pulseEdge 1.6s infinite ease-in-out;
}

@keyframes pulseEdge {
  0% {
    stroke-opacity: 0.4;
  }
  50% {
    stroke-opacity: 1;
  }
  100% {
    stroke-opacity: 0.4;
  }
}

/* Hover sur les nœuds */
.vue-flow__node:hover {
  transform: scale(1.05);
  box-shadow: 0 6px 16px rgba(255, 255, 255, 0.2);
}

/* Handlers agrandis et lumineux */
.vue-flow__handle {
  width: 14px !important;
  height: 14px !important;
  background: #facc15 !important;
  border: 2px solid #000 !important;
  box-shadow: 0 0 6px rgba(250, 204, 21, 0.8);
  transition: all 0.2s ease;
}

.vue-flow__handle:hover {
  transform: scale(1.3);
  box-shadow: 0 0 12px rgba(250, 204, 21, 1);
}
</style>
