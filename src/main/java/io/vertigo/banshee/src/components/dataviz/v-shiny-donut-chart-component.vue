<template>
  <div class="shiny-chart-container">
    <h3 class="shiny-component-title">{{ data.title || 'Shiny Donut Chart' }}</h3>
    <div class="chart-canvas-wrapper">
      <canvas :id="canvasId"></canvas>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
// Assuming 'Chart' is an external library, you might need to import it like:
// import { Chart } from 'chart.js'; // Adjust path as necessary

declare const Chart: any; // Declare Chart to avoid TypeScript errors if not imported

export default defineComponent({
  name: 'VShinyDonutChartComponent',
  props: {
    data: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      canvasId: `shiny-donutchart-${Math.random().toString(36).substr(2, 9)}`,
    };
  },
  mounted() {
    const ctx = document.getElementById(this.canvasId) as HTMLCanvasElement | null;
    if (!ctx) {
      console.error(`Canvas element not found for ID: ${this.canvasId}`);
      return;
    }
    const baseColors = ['#3182CE', '#63B3ED', '#4299E1', '#319795', '#81E6D9', '#F6AD55', '#F6E05E', '#FEB2B2', '#BEE3F8', '#C6F6D5'];

    let datasetsConfig;

    if (this.data.series && this.data.series.length > 0) {
      datasetsConfig = this.data.series.map((serie: any, serieIndex: number) => ({
        label: serie.name,
        data: serie.data || [],
        backgroundColor: baseColors.map(color => color.replace('1', (0.8 - serieIndex * 0.1).toFixed(1))),
        borderColor: baseColors.map(color => color.replace('1', (1 - serieIndex * 0.1).toFixed(1))),
        borderWidth: 1,
      }));
    } else {
      datasetsConfig = [{
        data: [],
        backgroundColor: baseColors,
        borderColor: baseColors.map(color => color.replace('0.8', '1')),
        borderWidth: 1,
      }];
    }

    new Chart(ctx, {
      type: 'doughnut',
      data: {
        labels: this.data.labels,
        datasets: datasetsConfig,
      },
      options: {
        plugins: {
          legend: {
            labels: {
              color: '#A0AEC0',
            },
          },
        },
      },
    });
  },
});
</script>

<style scoped>
.shiny-chart-container {
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

.chart-canvas-wrapper {
  position: relative;
  width: 100%;
  max-width: 400px; /* Adjust as needed */
  margin: 0 auto;
}

canvas {
  max-width: 100%;
  height: auto;
}
</style>