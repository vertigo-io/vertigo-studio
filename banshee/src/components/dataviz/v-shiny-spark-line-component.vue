<template>
  <div class="shiny-sparkline-container">
    <h3 class="shiny-component-title">{{ data.title || 'Shiny Sparkline' }}</h3>
    <canvas :id="canvasId" class="shiny-sparkline-canvas"></canvas>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
// Assuming 'Chart' is an external library, you might need to import it like:
// import { Chart } from 'chart.js'; // Adjust path as necessary

declare const Chart: any; // Declare Chart to avoid TypeScript errors if not imported

export default defineComponent({
  name: 'VShinySparkLineComponent',
  props: {
    data: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      canvasId: `sparkline-${Math.random().toString(36).substr(2, 9)}`,
    };
  },
  mounted() {
    const target = document.getElementById(this.canvasId) as HTMLCanvasElement | null;
    if (!target) {
      console.error(`Canvas element not found for ID: ${this.canvasId}`);
      return;
    }
    if (!this.data.values || this.data.values.length === 0) {
      return;
    }
    new Chart(target, {
      type: 'line',
      data: {
        labels: this.data.values.map((_: any, i: number) => i + 1),
        datasets: [{
          data: this.data.values,
          borderColor: 'rgba(75, 192, 192, 1)',
          backgroundColor: 'rgba(75, 192, 192, 0.2)',
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
  },
});
</script>

<style scoped>
.shiny-sparkline-container {
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

.shiny-sparkline-canvas {
  height: 100px; /* Adjust height as needed */
  width: 100%;
}
</style>