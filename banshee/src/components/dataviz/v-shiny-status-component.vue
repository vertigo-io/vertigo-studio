<template>
  <div class="shiny-status-container">
    <h3 class="shiny-component-title">{{ data.title || 'Shiny Status' }}</h3>
    <div class="shiny-status-indicators">
      <div v-for="(type, index) in data.types" :key="index" class="shiny-status-indicator" :style="{ backgroundColor: colorMap[type] || 'gray' }"></div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

interface ShinyStatusData {
  title?: string;
  types: ('SUCCESS' | 'ERROR' | 'WARNING' | 'INFO' | 'NEUTRAL')[];
}

export default defineComponent({
  name: 'VShinyStatusComponent',
  props: {
    data: {
      type: Object as () => ShinyStatusData,
      required: true,
    },
  },
  data() {
    return {
      colorMap: {
        SUCCESS: 'green',
        ERROR: 'red',
        WARNING: 'orange',
        INFO: 'blue',
        NEUTRAL: 'gray',
      },
    };
  },
});
</script>

<style scoped>
.shiny-status-container {
  background-color: #1A202C;
  padding: 15px;
  border-radius: 8px;
  color: #CBD5E0;
  text-align: center;
}

.shiny-component-title {
  color: #E2E8F0;
  margin-bottom: 15px;
}

.shiny-status-indicators {
  display: flex;
  justify-content: center;
  gap: 10px;
}

.shiny-status-indicator {
  width: 20px;
  height: 20px;
  border-radius: 50%;
  border: 1px solid #4A5568;
}
</style>