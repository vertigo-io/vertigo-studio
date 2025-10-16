<template>
  <div class="flow-tb-node">
    <Handle type="target" :position="Position.Top" />
    <div class="node-icon" :style="{ backgroundColor: iconBgColor }">
      {{ displayIcon }}
    </div>
    <div class="node-label">
      {{ data.label }}
    </div>
    <Handle type="source" :position="Position.Bottom" />
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
.flow-tb-node {
  display: flex;
  flex-direction: column; /* Arrange icon and label vertically */
  align-items: center;
  justify-content: center;
  width: 120px; /* Fixed width for circle */
  height: 120px; /* Fixed height for circle */
  text-align: center;
  font-size: 0.9em;
  font-weight: bold;
  color: #FFFFFF; /* White text */
  background-color: #2E3338; /* Dark gray background */
  border: 3px solid #E0E0E0; /* Thinner, light gray border */
  border-radius: 50%; /* Circular */
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
  margin-bottom: 5px; /* Space between icon and label */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.node-label {
  max-width: 90%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
