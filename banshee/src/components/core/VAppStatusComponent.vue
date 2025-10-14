<template>
  <div id="status" :class="statusClass">
    <div class="status-dot"></div>
    <span id="status-text">{{ statusText }}</span>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';

const statusClass = ref('status-disconnected');
const statusText = ref('Connecting');

const connectWebSocket = () => {
  // Assuming WebSocket is managed globally or provided/injected
  // For now, we'll use window.ws as a placeholder
  const ws = new WebSocket('ws://localhost:8080');

  ws.onopen = () => {
    statusClass.value = 'status-connected';
    statusText.value = 'Connected';
    (window as any).ws = ws; // Make WebSocket globally accessible for App.vue
  };

  ws.onclose = () => {
    statusClass.value = 'status-disconnected';
    statusText.value = 'Disconnected';
    (window as any).ws = null; // Clear global WebSocket
  };

  ws.onerror = (error: Event) => {
    statusClass.value = 'status-error';
    statusText.value = 'Error';
    console.error('WebSocket Error:', error);
  };

  // App.vue will handle onmessage, so we don't set it here
};

onMounted(() => {
  connectWebSocket();
});
</script>

<style scoped>
/* Styles for the status component */
#status {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 0.9em;
  color: var(--general-text);
}

.status-dot {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background-color: grey;
  transition: background-color 0.3s ease;
}

#status.status-connected .status-dot {
  background-color: green;
}

#status.status-disconnected .status-dot {
  background-color: red;
}

#status.status-error .status-dot {
  background-color: orange;
}
</style>