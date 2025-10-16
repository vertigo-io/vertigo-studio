<template>
  <div class="flow-icon-node">
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
import { Handle, Position } from '@vue-flow/core'; // Import Handle and Position

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
.flow-icon-node {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
  text-align: center;
  font-size: 0.9em;
  font-weight: bold;
  color: #333; /* Default text color */
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
  margin-bottom: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.node-label {
  max-width: 90%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
