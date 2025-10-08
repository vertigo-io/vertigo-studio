<template>
  <div class="shiny-sankey-container">
    <h3 class="shiny-component-title">{{ data.title || 'Shiny Sankey Diagram' }}</h3>
    <canvas :id="canvasId" class="shiny-sankey-canvas"></canvas>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
// Assuming 'Chart' is an external library, you might need to import it like:
// import { Chart } from 'chart.js'; // Adjust path as necessary
// import 'chartjs-chart-sankey'; // Import the Sankey chart type if it's a plugin

declare const Chart: any; // Declare Chart to avoid TypeScript errors if not imported

export default defineComponent({
  name: 'VShinySankeyComponent',
  props: {
    data: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      canvasId: `sankey-${Math.random().toString(36).substr(2, 9)}`,
    };
  },
  mounted() {
    const target = document.getElementById(this.canvasId) as HTMLCanvasElement | null;
    if (!target) {
      console.error(`Canvas element not found for ID: ${this.canvasId}`);
      return;
    }

    new Chart(target, {
      type: 'sankey',
      data: {
        datasets: [{
          data: this.data.data,
          colorFrom: (c: any) => 'rgba(75, 192, 192, 0.8)',
          colorTo: (c: any) => 'rgba(153, 102, 255, 0.8)',
          colorMode: 'gradient',
        }],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          title: {
            display: true,
            text: this.data.title,
            color: '#E2E8F0',
          },
          legend: { display: false },
          tooltip: {
            backgroundColor: '#2D3748',
            titleColor: '#E2E8F0',
            bodyColor: '#CBD5E0',
            borderColor: '#4A5568',
            borderWidth: 1,
            callbacks: {
              label: (context: any) => {
                const { raw } = context;
                return `${raw.from} -> ${raw.to}: ${raw.flow}`;
              },
            },
          },
        },
      },
    });
  },
});
</script>

<style scoped>
.shiny-sankey-container {
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

.shiny-sankey-canvas {
  height: 400px; /* Adjust height as needed */
  width: 100%;
}
</style>