<template>
  <div class="chart-container">
    <div class="table-title">{{ data.title || 'Donut Chart' }}</div>
    <div class="chart-canvas-wrapper">
      <canvas :id="canvasId" class="chart-canvas"></canvas>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue';
import { ShinyChart } from '../../models/dataviz/chart/ShinyChart';

declare const Chart: any;

const props = defineProps<{
  data: ShinyChart
}>()

const canvasId = `shiny-donutchart-${Math.random().toString(36).substr(2, 9)}`;

onMounted(() => {
  const style = getComputedStyle(document.documentElement);
  const colors = [
    style.getPropertyValue('--json-key').trim(),
    style.getPropertyValue('--json-boolean').trim(),
    style.getPropertyValue('--json-string').trim(),
    style.getPropertyValue('--json-number').trim(),
    style.getPropertyValue('--status-error').trim(),
    style.getPropertyValue('--x-neon-blue').trim(),
  ];
  const textColor = style.getPropertyValue('--chakra-paragraph-text').trim();

  const ctx = document.getElementById(canvasId) as HTMLCanvasElement | null;
  if (!ctx) return;

  new Chart(ctx, {
    type: 'doughnut',
    data: {
      labels: props.data.labels || [],
      datasets: [{
        data: props.data.series[0]?.data || [],
        backgroundColor: colors,
        borderColor: style.getPropertyValue('--general-bg').trim(),
        borderWidth: 2,
      }],
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          position: 'right',
          labels: { color: textColor },
        },
      },
    },
  });
});
</script>

<style scoped>
/* All styles are now handled by the global style.css */
</style>