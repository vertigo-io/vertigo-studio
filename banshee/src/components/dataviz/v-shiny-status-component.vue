<template>
  <div class="chart-container">
    <div class="table-title">{{ data.title || 'Status' }}</div>
    <div class="shiny-status-indicators">
      <div v-for="(type, index) in data.types" :key="index" class="shiny-status-indicator" :style="{ backgroundColor: colorMap[type] }"></div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted } from 'vue';

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
  setup() {
    const colorMap = {
      SUCCESS: '',
      ERROR: '',
      WARNING: '',
      INFO: '',
      NEUTRAL: '',
    };

    onMounted(() => {
      const style = getComputedStyle(document.documentElement);
      colorMap.SUCCESS = style.getPropertyValue('--status-connected').trim();
      colorMap.ERROR = style.getPropertyValue('--status-error').trim();
      colorMap.WARNING = style.getPropertyValue('--yellow-accent').trim(); // Using yellow for warning
      colorMap.INFO = style.getPropertyValue('--x-neon-blue').trim(); // Using neon blue for info
      colorMap.NEUTRAL = style.getPropertyValue('--general-text').trim(); // Using general text color for neutral
    });

    return { colorMap };
  },
});
</script>

<style scoped>
/* All styles are now handled by the global style.css */
.shiny-status-indicators {
  display: flex;
  justify-content: center;
  gap: 10px;
}

.shiny-status-indicator {
  width: 20px;
  height: 20px;
  border-radius: 50%;
  border: 1px solid var(--status-border);
}
</style>