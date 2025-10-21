<template>
  <v-btn :color="data.color" @click="sendAction(data.action)">{{ data.text }}</v-btn>
</template>

<script setup lang="ts">
import { ShinyButtonCell } from '../../../models/data/table/cell/ShinyButtonCell';

const props = defineProps<{
  data: ShinyButtonCell;
}>();

const sendAction = (action: string) => {
  // Implement WebSocket logic to send the action to the backend
  console.log(`Action triggered: ${action}`);
  if ((window as any).ws) {
    (window as any).ws.send(JSON.stringify({ command: action }));
  } else {
    console.error("WebSocket connection not found on window instance.");
  }
};
</script>
