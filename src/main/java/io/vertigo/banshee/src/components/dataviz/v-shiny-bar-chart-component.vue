<template>
  <div class="shiny-chart-container">
    <h3 class="shiny-component-title">{{ data.title || 'Shiny Bar Chart' }}</h3>
    <canvas :id="canvasId"></canvas>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
// Assuming 'Chart' is an external library, you might need to import it like:
// import { Chart } from 'chart.js'; // Adjust path as necessary

declare const Chart: any; // Declare Chart to avoid TypeScript errors if not imported

export default defineComponent({
  name: 'VShinyBarChartComponent',
  props: {
    data: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      canvasId: `shiny-barchart-${Math.random().toString(36).substr(2, 9)}`,
    };
  },
  mounted() {
    const colors = [
      'rgba(144, 205, 244, 0.8)', // blue
      'rgba(160, 217, 144, 0.8)', // green
      'rgba(244, 144, 144, 0.8)', // red
      'rgba(244, 224, 144, 0.8)', // yellow
      'rgba(192, 144, 244, 0.8)', // purple
    ];

    const ctx = document.getElementById(this.canvasId) as HTMLCanvasElement | null;
    if (!ctx) {
      console.error(`Canvas element not found for ID: ${this.canvasId}`);
      return;
    }
    new Chart(ctx, {
      type: 'bar',
      data: {
        labels: this.data.labels || [],
        datasets: this.data.series.map((serie: any, index: number) => ({
          label: serie.name,
          data: serie.data || [],
          backgroundColor: colors[index % colors.length],
          borderColor: colors[index % colors.length].replace('0.8', '1'),
          borderWidth: 1,
        })),
      },
      options: {
        scales: {
          y: {
            beginAtZero: true,
            ticks: {
              color: '#A0AEC0',
            },
            grid: {
              color: '#2D3748',
            },
          },
          x: {
            ticks: {
              color: '#A0AEC0',
            },
            grid: {
              color: '#2D3748',
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