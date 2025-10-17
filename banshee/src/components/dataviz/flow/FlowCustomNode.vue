<template>
  <div :class="nodeTypeClass">
    <!-- Handles based on node type -->
    <template v-if="data.nodeType === NodeType.LR">
      <Handle type="target" :position="Position.Left" />
      <Handle type="source" :position="Position.Right" />
    </template>
    <template v-else-if="data.nodeType === NodeType.TB">
      <Handle type="target" :position="Position.Top" />
      <Handle type="source" :position="Position.Bottom" />
    </template>
    <template v-else-if="data.nodeType === NodeType.LL">
      <Handle type="target" :position="Position.Left" />
    </template>
    <template v-else-if="data.nodeType === NodeType.RR">
      <Handle type="source" :position="Position.Right" />
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
import { NodeType } from '../../../models/dataviz/flow/NodeType'; // Import NodeType enum

const props = defineProps<{
  data: {
    label: string;
    icon?: string;
    iconColor?: string;
    nodeType: NodeType; // Expect nodeType from data
  };
}>();

const defaultIcon = '⚙️'; // Gear icon
const defaultIconColor = '#1DA1F2'; // Neon blue

const displayIcon = computed(() => props.data.icon || defaultIcon);
const iconBgColor = computed(() => props.data.iconColor || defaultIconColor);

const nodeTypeClass = computed(() => {
  switch (props.data.nodeType) {
    case NodeType.LR: return 'flow-lr-node';
    case NodeType.TB: return 'flow-tb-node';
    case NodeType.LL: return 'flow-ll-node';
    case NodeType.RR: return 'flow-rr-node';
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
</style>
