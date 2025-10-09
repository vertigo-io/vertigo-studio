<template>
  <div class="shiny-chart-container">
    <h3 class="shiny-component-title">{{ data.title || 'Shiny Radar Chart' }}</h3>
    <canvas :id="canvasId"></canvas>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
// Assuming 'Chart' is an external library, you might need to import it like:
// import { Chart } from 'chart.js'; // Adjust path as necessary

declare const Chart: any; // Declare Chart to avoid TypeScript errors if not imported

export default defineComponent({
  name: 'VShinyRadarChartComponent',
  props: {
    data: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      canvasId: `shiny-radarchart-${Math.random().toString(36).substr(2, 9)}`,
    };
  },
  mounted() {
    const ctx = document.getElementById(this.canvasId) as HTMLCanvasElement | null;
    if (!ctx) {
      console.error(`Canvas element not found for ID: ${this.canvasId}`);
      return;
    }
    const datasets = this.data.series.map((serie: any, index: number) => {
      const color = ['#3182CE', '#63B3ED', '#4299E1', '#319795', '#81E6D9'][index % 5];
      return {
        label: serie.name,
        data: serie.data,
        backgroundColor: color + '80', // Add alpha transparency
        borderColor: color,
        borderWidth: 1,
      };
    });

    new Chart(ctx, {
      type: 'radar',
      data: {
        labels: this.data.labels,
        datasets: datasets,
      },
      options: {
        scales: {
          r: {
            angleLines: {
              color: '#4A5568',
            },
            grid: {
              color: '#4A5568',
            },
            pointLabels: {
              color: '#A0AEC0',
            },
            ticks: {
              color: '#A0AEC0',
              backdropColor: '#1A202C',
            },
          },
        },
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

canvas {
  max-width: 100%;
  height: auto;
}
</style>