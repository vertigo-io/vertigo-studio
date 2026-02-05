<template>
  <div :class="nodeTypeClass">
    <!-- Handles based on node type -->
    <template v-if="data.nodeType === ShinyFlowNodeType.LR">
      <Handle type="target" :position="Position.Left" class="flow-handle round" />
      <Handle type="source" :position="Position.Right" class="flow-handle round" />
      <Handle type="source" :position="Position.Bottom" class="flow-handle rectangle" />
      <Handle type="source" :position="Position.Top" class="flow-handle rectangle" />
    </template>
    <template v-else-if="data.nodeType === ShinyFlowNodeType.TB">
      <Handle type="target" :position="Position.Top" class="flow-handle round" />
      <Handle type="source" :position="Position.Bottom" class="flow-handle round" />
    </template>
    <template v-else-if="data.nodeType === ShinyFlowNodeType.LL">
      <Handle type="target" :position="Position.Left" class="flow-handle round" />
    </template>
    <template v-else-if="data.nodeType === ShinyFlowNodeType.RR">
      <Handle type="source" :position="Position.Right" class="flow-handle round" />
    </template>

    <div class="node-icon" :style="{ backgroundColor: iconBgColor }">
      {{ displayIcon }}
    </div>
    <div class="node-label">
      {{ data.label }}
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineProps, computed } from 'vue';
import { Handle, Position } from '@vue-flow/core';
import { ShinyFlowNodeType } from '../../../models/dataviz/flow/ShinyFlowNodeType'; // Import ShinyFlowNodeType enum

const props = defineProps<{
  data: {
    label: string;
    icon?: string;
    iconColor?: string;
    nodeType: ShinyFlowNodeType; // Expect nodeType from data
  };
}>();

const defaultIcon = '⚙️'; // Gear icon
const defaultIconColor = '#1DA1F2'; // Neon blue

const displayIcon = computed(() => props.data.icon || defaultIcon);
const iconBgColor = computed(() => props.data.iconColor || defaultIconColor);

const nodeTypeClass = computed(() => {
  switch (props.data.nodeType) {
    case ShinyFlowNodeType.LR: return 'flow-lr-node';
    case ShinyFlowNodeType.TB: return 'flow-tb-node';
    case ShinyFlowNodeType.LL: return 'flow-ll-node';
    case ShinyFlowNodeType.RR: return 'flow-rr-node';
    default: return '';
  }
});
</script>

<style scoped>
/* Base styles for icon and label, common to all nodes */
.node-icon {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.2em;
  color: white; /* Icon color */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.node-label {
  max-width: 120px; /* Limit label width */
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

/* Specific styles for LR node */
.flow-lr-node {
  display: flex;
  flex-direction: row; /* Arrange icon and label horizontally */
  align-items: center;
  justify-content: center;
  text-align: center;
  font-size: 0.9em;
  font-weight: bold;
  color: #FFFFFF; /* White text */
  background-color: #2E3338; /* Dark gray background */
  border: 3px solid #E0E0E0; /* Thinner, light gray border */
  padding: 10px;
  box-sizing: border-box;
  position: relative; /* For handle positioning */
  width: 180px; /* Wider for rectangle */
  height: 60px; /* Shorter for rectangle */
  border-radius: 15px; /* Rounded rectangle */
}
.flow-lr-node .node-icon {
  margin-right: 8px;
}

/* Specific styles for TB node */
.flow-tb-node {
  display: flex;
  flex-direction: column; /* Arrange icon and label vertically */
  align-items: center;
  justify-content: center;
  text-align: center;
  font-size: 0.9em;
  font-weight: bold;
  color: #FFFFFF; /* White text */
  background-color: #2E3338; /* Dark gray background */
  border: 3px solid #E0E0E0; /* Thinner, light gray border */
  padding: 10px;
  box-sizing: border-box;
  position: relative; /* For handle positioning */
  width: 120px; /* Fixed width for circle */
  height: 120px; /* Fixed height for circle */
  border-radius: 50%; /* Circular */
}
.flow-tb-node .node-icon {
  margin-bottom: 5px;
}

/* Specific styles for LL node */
.flow-ll-node {
  display: flex;
  flex-direction: row; /* Arrange icon and label horizontally */
  align-items: center;
  justify-content: center;
  text-align: center;
  font-size: 0.9em;
  font-weight: bold;
  color: #FFFFFF; /* White text */
  background-color: #2E3338; /* Dark gray background */
  border: 3px solid #E0E0E0; /* Thinner, light gray border */
  padding: 10px;
  box-sizing: border-box;
  position: relative; /* For handle positioning */
  width: 180px; /* Wider for rectangle */
  height: 60px; /* Shorter for rectangle */
  border-top-left-radius: 15px; /* Rectangular left */
  border-bottom-left-radius: 15px; /* Rectangular left */
  border-top-right-radius: 9999px; /* Very rounded right */
  border-bottom-right-radius: 9999px; /* Very rounded right */
}
.flow-ll-node .node-icon {
  margin-right: 8px;
}

/* Specific styles for RR node */
.flow-rr-node {
  display: flex;
  flex-direction: row; /* Arrange icon and label horizontally */
  align-items: center;
  justify-content: center;
  text-align: center;
  font-size: 0.9em;
  font-weight: bold;
  color: #FFFFFF; /* White text */
  background-color: #2E3338; /* Dark gray background */
  border: 3px solid #E0E0E0; /* Thinner, light gray border */
  padding: 10px;
  box-sizing: border-box;
  position: relative; /* For handle positioning */
  width: 180px; /* Wider for rectangle */
  height: 60px; /* Shorter for rectangle */
  border-top-left-radius: 9999px; /* Very rounded left */
  border-bottom-left-radius: 9999px; /* Very rounded left */
  border-top-right-radius: 15px; /* Rectangular right */
  border-bottom-right-radius: 15px; /* Rectangular right */
}
.flow-rr-node .node-icon {
  margin-left: 8px; /* Icon on the right for RR */
}

/* Style pour un handle en forme de rond gris clair, taille d'un "O" (~12px) */
.flow-handle.round {
  background: #d3d3d3; /* Gris clair */
  width: 20px;
  height: 20px;
  border-radius: 50%;
  z-index: -1;
}

/* Style pour un handle en forme de rectangle horizontal, deux fois plus long que large */
.flow-handle.rectangle {
  background: #d3d3d3; /* Gris clair */
  width: 20px; /* Deux fois plus long */
  height: 15px; /* Largeur de base */
  border-radius: 2px; /* Coins légèrement arrondis */
  z-index: -1;
}
/* Positionnement pour deux handles centrés en bas */
.flow-lr-dual-node.handle-1 {
  left: calc(50% - 20px); /* Décalage à gauche du centre */
}
.flow-lr-dual-node.handle-2 {
  left: calc(50% + 20px); /* Décalage à droite du centre */
}

/* Positionnement pour trois handles centrés en bas */
.flow-lr-triple-node .handle-1 {
}

.flow-lr-triple-node .handle-2 {
  left: 50%; /* Centre exact */
  transform: translateX(-50%); /* Ajustement pour centrer parfaitement */
}
.flow-lr-triple-node handle-3 {
  left: calc(50% ); /* Centre */
  transform: translateX(+50%); /* Ajustement pour centrer parfaitement */
}
</style>
