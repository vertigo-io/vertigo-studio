<template>
  <div class="flow-lr-node">
    <Handle type="target" :position="Position.Left" />
    <div class="node-icon" :style="{ backgroundColor: iconBgColor }">
      {{ displayIcon }}
    </div>
    <div class="node-label">
      {{ data.label }}
    </div>
    <Handle type="source" :position="Position.Right" />
  </div>
</template>

<script setup lang="ts">
import { defineProps, computed } from 'vue';
import { Handle, Position } from '@vue-flow/core';

const props = defineProps<{
  data: {
    label: string;
    icon?: string;
    iconColor?: string;
  };
}>();

const defaultIcon = '⚙️'; // Gear icon
const defaultIconColor = '#1DA1F2'; // Neon blue

const displayIcon = computed(() => props.data.icon || defaultIcon);
const iconBgColor = computed(() => props.data.iconColor || defaultIconColor);
</script>

<style scoped>
.flow-lr-node {
  display: flex;
  flex-direction: row; /* Arrange icon and label horizontally */
  align-items: center;
  justify-content: center;
  width: 180px; /* Wider for rectangle */
  height: 60px; /* Shorter for rectangle */
  text-align: center;
  font-size: 0.9em;
  font-weight: bold;
  color: #FFFFFF; /* White text */
  background-color: #2E3338; /* Dark gray background */
  border: 3px solid #E0E0E0; /* Thinner, light gray border */
  border-radius: 15px; /* Rounded rectangle */
  padding: 10px;
  box-sizing: border-box;
  position: relative; /* For handle positioning */
}

.node-icon {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.2em;
  color: white; /* Icon color */
  margin-right: 8px; /* Space between icon and label */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.node-label {
  max-width: 120px; /* Limit label width */
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
