<template>
  <div class="workflow-dark">
    <VueFlow v-model="elements" :fit-view-on-init="true" :node-types="nodeTypes">
      <Background variant="dots" :gap="20" :size="1" color="#4A4A4A" />
      <Controls />
    </VueFlow>

    <!-- Bouton Auto Layout -->
    <button class="auto-layout-btn" @click="autoLayout">
      <svg xmlns="http://www.w3.org/2000/svg" class="icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M4 4h16v16H4zM9 9h6v6H9z" />
      </svg>
      Auto layout
    </button>
  </div>
</template>

<script setup lang="ts">
import { ref, watchEffect, onMounted } from 'vue' // Import onMounted
import { VueFlow, useVueFlow, type Elements } from '@vue-flow/core'
import { Background } from '@vue-flow/background'
import { Controls } from '@vue-flow/controls'
import dagre from '@dagrejs/dagre' // dagre import
import { Graph } from '@dagrejs/graphlib' // Graph import
import FlowLRNode from './flow/FlowLRNode.vue'; // Import custom node component
import FlowTBNode from './flow/FlowTBNode.vue'; // Import custom node component
import FlowLLNode from './flow/FlowLLNode.vue'; // Import custom node component
import FlowRRNode from './flow/FlowRRNode.vue'; // Import custom node component
import '@vue-flow/core/dist/style.css'
import '@vue-flow/core/dist/theme-default.css'
import { ShinyFlow } from '../../models/dataviz/flow/ShinyFlow'
import { ShinyFlowNode } from '@/models/dataviz/flow/ShinyFlowNode'
import { ShinyFlowEdge } from '@/models/dataviz/flow/ShinyFlowEdge'
import { NodeType } from '../../models/dataviz/flow/NodeType'; // Import NodeType enum

const props = defineProps<{ data: ShinyFlow }>()
const { fitView } = useVueFlow(); // Only destructure fitView
const elements = ref<Elements>([])

// Define nodeTypes object
const nodeTypes = {
  [NodeType.LR]: FlowLRNode,
  [NodeType.TB]: FlowTBNode,
  [NodeType.LL]: FlowLLNode,
  [NodeType.RR]: FlowRRNode,
};

// 🎨 Palette Make-like
const nodeColors = [
  '#6366f1', // Indigo
  '#10b981', // Emerald
  '#f59e0b', // Amber
  '#ef4444', // Red
  '#3b82f6', // Blue
]
// ... rest of script ...



// 🧠 Fonction de layout avec Dagre
function layoutElements(nodes: ShinyFlowNode[], edges: ShinyFlowEdge[], direction = 'LR') {
  const dagreGraph = new dagre.graphlib.Graph()
  dagreGraph.setDefaultEdgeLabel(() => ({}))
  dagreGraph.setGraph({ rankdir: direction, nodesep: 80, ranksep: 100 })

  nodes.forEach(node => dagreGraph.setNode(node.id, { width: 120, height: 120 }))
  edges.forEach(edge => dagreGraph.setEdge(edge.source, edge.target))

  dagre.layout(dagreGraph)

  nodes.forEach(node => {
    const pos = dagreGraph.node(node.id)
    node.position = { x: pos.x - 100, y: pos.y - 30 }
  })

  return { nodes, edges }
}

// 🧩 Création initiale
watchEffect(() => {
  // 🎨 Palette Make-like
  const nodeColors = [
    '#6366f1', // Indigo
    '#10b981', // Emerald
    '#f59e0b', // Amber
    '#ef4444', // Red
    '#3b82f6', // Blue
  ]

  // Icons for nodes
  const nodeIcons = ['📦', '💳', '🚚', '🧾', '✅']; // Example icons

  if (props.data) {
    const nodes = props.data.nodes.map((node, i) => ({
      id: node.id,
      position: { x: 0, y: 0 },
      type: node.nodeType, // Use nodeType from backend
      data: {
        label: node.label,
        icon: nodeIcons[i % nodeIcons.length], // Assign icon
        iconColor: nodeColors[i % nodeColors.length], // Assign icon color
      },
      // Remove class and style here, as they are handled by custom node components
    }))

    const edges = props.data.edges.map(edge => ({
      id: edge.id,
      source: edge.source,
      target: edge.target,
      label: edge.label,
      type: edge.type || 'smoothstep',
      animated: true,
      style: { stroke: '#818cf8', strokeWidth: 2 },
      labelStyle: { fill: '#a5b4fc', fontWeight: 500, fontSize: '12px' },
    }))

    const layouted = layoutElements(nodes, edges, 'LR')
    elements.value = [...layouted.nodes, ...layouted.edges]

    setTimeout(() => fitView(), 80)
  }
})

// ⚙️ Bouton manuel
function autoLayout() {
  const nodes = elements.value.filter(e => e.position)
  const edges = elements.value.filter(e => e.source)
  const layouted = layoutElements(nodes, edges, 'LR')
  elements.value = [...layouted.nodes, ...layouted.edges]
  fitView()
}
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

/* Edges lumineux */
.vue-flow__edge-path {
  stroke-linecap: round;
  animation: pulseEdge 1.6s infinite ease-in-out;
}

@keyframes pulseEdge {
  0% { stroke-opacity: 0.4; }
  50% { stroke-opacity: 1; }
  100% { stroke-opacity: 0.4; }
}

/* Hover sur les nœuds */
.vue-flow__node:hover {
  transform: scale(1.05);
  box-shadow: 0 6px 16px rgba(255, 255, 255, 0.2);
}

/* 🌟 HANDLES façon Make.com */
.vue-flow__handle {
  width: 22px !important;
  height: 22px !important;
  background: linear-gradient(145deg, #e5e7eb, #d1d5db) !important;
  border: 2px solid #f9fafb !important;
  border-radius: 50% !important;
  box-shadow:
    0 2px 4px rgba(0, 0, 0, 0.25),
    inset 0 1px 3px rgba(255, 255, 255, 0.3) !important;
  opacity: 1 !important;
  z-index: 20;
  transition: all 0.2s ease;
  cursor: crosshair;
}

.vue-flow__handle:hover {
  transform: scale(1.3);
  background: #f3f4f6 !important;
  box-shadow:
    0 0 10px rgba(255, 255, 255, 0.5),
    inset 0 2px 4px rgba(0, 0, 0, 0.15) !important;
}

.vue-flow__handle-left,
.vue-flow__handle-top {
  background: linear-gradient(145deg, #d4d4d8, #f3f4f6) !important;
}

.vue-flow__handle-right,
.vue-flow__handle-bottom {
  background: linear-gradient(145deg, #f3f4f6, #d4d4d8) !important;
}

/* Positionnement élargi */
.vue-flow__handle-top {
  top: -12px !important;
  left: 50% !important;
  transform: translateX(-50%) !important;
}

.vue-flow__handle-bottom {
  bottom: -12px !important;
  left: 50% !important;
  transform: translateX(-50%) !important;
}

.vue-flow__handle-left {
  left: -12px !important;
  top: 50% !important;
  transform: translateY(-50%) !important;
}

.vue-flow__handle-right {
  right: -12px !important;
  top: 50% !important;
  transform: translateY(-50%) !important;
}

/* 🌟 Bouton Auto Layout */
.auto-layout-btn {
  position: absolute;
  bottom: 18px;
  right: 18px;
  background: linear-gradient(135deg, #4f46e5, #7c3aed);
  color: #fff;
  border: none;
  border-radius: 12px;
  padding: 10px 16px;
  font-weight: 600;
  font-size: 14px;
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  box-shadow: 0 4px 8px rgba(79, 70, 229, 0.4);
  transition: all 0.2s ease-in-out;
}

.auto-layout-btn:hover {
  transform: scale(1.05);
  box-shadow: 0 6px 14px rgba(79, 70, 229, 0.6);
}

.auto-layout-btn .icon {
  width: 18px;
  height: 18px;
}
</style>
