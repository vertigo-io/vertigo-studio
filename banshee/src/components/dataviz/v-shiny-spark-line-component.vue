<template>
  <div class="sparkline-container">
    <div class="table-title">{{ data.title || 'Sparkline' }}</div>
    <canvas :id="canvasId" class="sparkline-canvas"></canvas>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted } from 'vue';
import { ShinySparkline } from '../../models/dataviz/sparkline/ShinySparkline';

declare const Chart: any;

export default defineComponent({
  name: 'VShinySparkLineComponent',
  props: {
    data: {
      type: Object as () => ShinySparkline,
      required: true,
    },
  },
  setup(props) {
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

    return { canvasId };
  },
});
</script>

<style scoped>
/* All styles are now handled by the global style.css */
</style>