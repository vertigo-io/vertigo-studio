<template>
  <div class="sparkline-container">
    <div class="table-title">{{ data.title || 'Sparkline' }}</div>
    <canvas :id="canvasId" class="sparkline-canvas"></canvas>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue';
import { ShinySparkline } from '../../models/text/sparkline/ShinySparkline';

declare const Chart: any;

const props = defineProps<{
  data: ShinySparkline
}>()

const canvasId = `sparkline-${Math.random().toString(36).substr(2, 9)}`;

onMounted(() => {
  const style = getComputedStyle(document.documentElement);
  const sparklineColor = style.getPropertyValue('--x-neon-blue').trim(); // Using a distinct accent color for sparkline

  const target = document.getElementById(canvasId) as HTMLCanvasElement | null;
  if (!target) return;
  if (!props.data.values || props.data.values.length === 0) {
    return;
  }
  new Chart(target, {
    type: 'line',
    data: {
      labels: props.data.values.map((_: any, i: number) => i + 1),
      datasets: [{
        data: props.data.values,
        borderColor: sparklineColor,
        backgroundColor: sparklineColor + '33', // Add alpha transparency
        borderWidth: 1,
        pointRadius: 0,
        fill: true,
        tension: 0.4,
      }],
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: { display: false },
        tooltip: { enabled: false },
      },
      scales: {
        x: { display: false },
        y: { display: false },
      },
      elements: {
        line: {
          borderWidth: 1,
        },
      },
    },
  });
});
</script>

<style scoped>
.sparkline-canvas {
	width: 100%;
	height: 50px; /* Typical height for a sparkline */
	max-height: 50px;
	display: block;
}

.sparkline-container {
	width: 200px;
	height: 50px; /* Hauteur fixe */
	max-height: 50px;
	position: relative;
}
</style>