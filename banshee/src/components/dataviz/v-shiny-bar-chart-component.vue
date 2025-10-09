<template>
  <div class="chart-container">
    <div class="table-title">{{ data.title || 'Bar Chart' }}</div>
    <canvas :id="canvasId" class="chart-canvas"></canvas>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted } from 'vue';
import { ShinyChart } from '../../models/ShinyChart';

declare const Chart: any;

export default defineComponent({
  name: 'VShinyBarChartComponent',
  props: {
    data: {
      type: Object as () => ShinyChart,
      required: true,
    },
  },
  setup(props) {
    const canvasId = `shiny-barchart-${Math.random().toString(36).substr(2, 9)}`;

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

      const ctx = document.getElementById(canvasId) as HTMLCanvasElement | null;
      if (!ctx) {
        console.error(`Canvas element not found for ID: ${canvasId}`);
        return;
      }

      new Chart(ctx, {
        type: 'bar',
        data: {
          labels: props.data.labels || [],
          datasets: props.data.series.map((serie: any, index: number) => ({
            label: serie.name,
            data: serie.data || [],
            backgroundColor: colors[index % colors.length],
            borderColor: colors[index % colors.length],
            borderWidth: 1,
          })),
        },
        options: {
          scales: {
            y: {
              beginAtZero: true,
              ticks: {
                color: textColor,
              },
              grid: {
                color: gridColor,
              },
            },
            x: {
              ticks: {
                color: textColor,
              },
              grid: {
                color: gridColor,
              },
            },
          },
          plugins: {
            legend: {
              labels: {
                color: textColor,
              },
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