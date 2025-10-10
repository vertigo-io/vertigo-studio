<template>
  <div class="chart-container">
    <div class="table-title">{{ data.title || 'Radar Chart' }}</div>
    <canvas :id="canvasId" class="chart-canvas"></canvas>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue';
import { ShinyChart } from '../../models/dataviz/chart/ShinyChart';

declare const Chart: any;

const props = defineProps<{
  data: ShinyChart
}>()

const canvasId = `shiny-radarchart-${Math.random().toString(36).substr(2, 9)}`;

onMounted(() => {
  const style = getComputedStyle(document.documentElement);
  const colors = [
    style.getPropertyValue('--json-key').trim(),
    style.getPropertyValue('--json-boolean').trim(),
    style.getPropertyValue('--json-string').trim(),
    style.getPropertyValue('--json-number').trim(),
    style.getPropertyValue('--status-error').trim(),
  ];
  const textColor = style.getPropertyValue('--chakra-paragraph-text').trim();
  const gridColor = style.getPropertyValue('--assistant-accent').trim();
  const generalBg = style.getPropertyValue('--general-bg').trim();

  const ctx = document.getElementById(canvasId) as HTMLCanvasElement | null;
  if (!ctx) return;

  const datasets = props.data.series.map((serie: any, index: number) => {
    const color = colors[index % colors.length];
    return {
      label: serie.name,
      data: serie.data,
      backgroundColor: color + '33', // Add alpha transparency
      borderColor: color,
      borderWidth: 1,
    };
  });

  new Chart(ctx, {
    type: 'radar',
    data: {
      labels: props.data.labels,
      datasets: datasets,
    },
    options: {
      scales: {
        r: {
          angleLines: { color: gridColor },
          grid: { color: gridColor },
          pointLabels: { color: textColor },
          ticks: { color: textColor, backdropColor: generalBg },
        },
      },
      plugins: {
        legend: {
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