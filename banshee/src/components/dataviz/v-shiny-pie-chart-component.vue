<template>
  <div class="shiny-chart-container">
    <h3 class="shiny-component-title">{{ data.title || 'Shiny Pie Chart' }}</h3>
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
  name: 'VShinyPieChartComponent',
  props: {
    data: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      canvasId: `shiny-piechart-${Math.random().toString(36).substr(2, 9)}`,
    };
  },
  mounted() {
    const ctx = document.getElementById(this.canvasId) as HTMLCanvasElement | null;
    if (!ctx) {
      console.error(`Canvas element not found for ID: ${this.canvasId}`);
      return;
    }
    const baseColors = [
      '#3182CE', '#63B3ED', '#4299E1', '#319795', '#81E6D9',
      '#F6AD55', '#F6E05E', '#FEB2B2', '#BEE3F8', '#C6F6D5'
    ];

    let chartType: string;
    let datasetsConfig: any[];

    if (this.data.series && this.data.series.length > 1) {
      // Multi-series: utiliser des doughnuts concentriques
      chartType = 'doughnut';
      const seriesCount = this.data.series.length;

      datasetsConfig = this.data.series.map((serie: any, serieIndex: number) => {
        // Calculer le cutout pour créer des anneaux concentriques
        const cutoutPercentage = 30 + (serieIndex * (50 / seriesCount));

        // Générer des couleurs distinctes pour chaque série
        const colorOffset = serieIndex * Math.floor(baseColors.length / seriesCount);
        const serieColors = serie.data.map((_: any, index: number) => {
          const colorIndex = (colorOffset + index) % baseColors.length;
          return baseColors[colorIndex];
        });

        return {
          label: serie.name,
          data: serie.data || [],
          backgroundColor: serieColors,
          borderColor: '#1A202C',
          borderWidth: 2,
          // Configurer le rayon pour chaque dataset
          weight: 1,
        };
      });
    } else if (this.data.series && this.data.series.length === 1) {
      // Single series: pie chart standard
      chartType = 'pie';
      datasetsConfig = [{
        data: this.data.series[0].data || [],
        backgroundColor: baseColors.slice(0, this.data.series[0].data.length),
        borderColor: '#1A202C',
        borderWidth: 2,
      }];
    } else {
      // Cas par défaut
      chartType = 'pie';
      datasetsConfig = [{
        data: [],
        backgroundColor: baseColors,
        borderColor: '#1A202C',
        borderWidth: 2,
      }];
    }

    new Chart(ctx, {
      type: chartType,
      data: {
        labels: this.data.labels,
        datasets: datasetsConfig,
      },
      options: {
        responsive: true,
        maintainAspectRatio: true,
        plugins: {
          legend: {
            position: 'right',
            labels: {
              color: '#A0AEC0',
              padding: 15,
              font: {
                size: 12,
              },
            },
          },
          tooltip: {
            backgroundColor: '#2D3748',
            titleColor: '#E2E8F0',
            bodyColor: '#CBD5E0',
            borderColor: '#4A5568',
            borderWidth: 1,
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