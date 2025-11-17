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
@keyframes pulse {
  0%, 100% {
    box-shadow: 0 0 8px currentColor;
  }
  50% {
    box-shadow: 0 0 12px currentColor;
  }
}

#status {
  position: absolute;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 4px 8px;
  font-size: 11px;
  font-weight: 500;
  color: var(--general-text);
  text-transform: uppercase;
  letter-spacing: 1px;
  background: var(--status-bg);
  border-radius: 12px;
  backdrop-filter: blur(10px);
  border: 1px solid var(--status-border);
}

.status-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background-color: grey;
  transition: background-color 0.3s ease;
  animation: pulse 2s infinite;
}

#status.status-connected .status-dot,
.status-connected .status-dot {
  background-color: var(--status-connected, green);
  box-shadow: 0 0 8px var(--status-connected, green);
}

#status.status-disconnected .status-dot,
.status-disconnected .status-dot {
  background-color: var(--status-disconnected, red);
  box-shadow: 0 0 8px var(--status-disconnected, red);
}

#status.status-error .status-dot,
.status-error .status-dot {
  background-color: var(--status-error, orange);
  box-shadow: 0 0 8px var(--status-error, orange);
}

#status-text {
  color: #CBD5E0;
}
</style>