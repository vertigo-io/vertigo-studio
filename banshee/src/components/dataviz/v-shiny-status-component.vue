<template>
  <div class="chart-container">
    <div class="table-title">{{ data.title || 'Status' }}</div>
    <div class="shiny-status-indicators">
      <div v-for="(type, index) in data.types" :key="index" class="shiny-status-indicator" :style="{ backgroundColor: colorMap[type] }"></div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive } from 'vue';
import { ShinyStatus } from '../../models/dataviz/status/ShinyStatus';
import { ShinyStatusType, getShinyStatusColor } from '../../models/dataviz/status/ShinyStatusType';

const props = defineProps<{
  data: ShinyStatus
}>()

const colorMap: Record<ShinyStatusType, string> = reactive({
  [ShinyStatusType.SUCCESS]: '',
  [ShinyStatusType.ERROR]: '',
  [ShinyStatusType.WARNING]: '',
  [ShinyStatusType.INFO]: '',
  [ShinyStatusType.NEUTRAL]: '',
});

onMounted(() => {
  colorMap.SUCCESS = getShinyStatusColor(ShinyStatusType.SUCCESS);
  colorMap.ERROR = getShinyStatusColor(ShinyStatusType.ERROR);
  colorMap.WARNING = getShinyStatusColor(ShinyStatusType.WARNING);
  colorMap.INFO = getShinyStatusColor(ShinyStatusType.INFO);
  colorMap.NEUTRAL = getShinyStatusColor(ShinyStatusType.NEUTRAL);
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