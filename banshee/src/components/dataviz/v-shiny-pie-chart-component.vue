<template>
  <div class="chart-container">
    <div class="table-title">{{ data.title || 'Pie Chart' }}</div>
    <div class="chart-canvas-wrapper">
      <canvas :id="canvasId" class="chart-canvas"></canvas>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted } from 'vue';
import { ShinyChart } from '../../models/dataviz/chart/ShinyChart';

declare const Chart: any;

export default defineComponent({
  name: 'VShinyPieChartComponent',
  props: {
    data: {
      type: Object as () => ShinyChart,
      required: true,
    },
  },
  setup(props) {
    const canvasId = `shiny-piechart-${Math.random().toString(36).substr(2, 9)}`;

    onMounted(() => {
      const style = getComputedStyle(document.documentElement);
      const colors = [
        style.getPropertyValue('--json-key').trim(),
        style.getPropertyValue('--json-boolean').trim(),
        style.getPropertyValue('--json-string').trim(),
        style.getPropertyValue('--json-number').trim(),
        style.getPropertyValue('--status-error').trim(),
        style.getPropertyValue('--x-neon-blue').trim(),
      ];
      const textColor = style.getPropertyValue('--chakra-paragraph-text').trim();
      const generalBg = style.getPropertyValue('--general-bg').trim();

      const ctx = document.getElementById(canvasId) as HTMLCanvasElement | null;
      if (!ctx) return;

      let chartType: string;
      let datasetsConfig: any[];

      if (props.data.series && props.data.series.length > 1) {
        chartType = 'doughnut';
        const seriesCount = props.data.series.length;

        datasetsConfig = props.data.series.map((serie: any, serieIndex: number) => {
          const cutoutPercentage = 30 + (serieIndex * (50 / seriesCount));
          const colorOffset = serieIndex * Math.floor(colors.length / seriesCount);
          const serieColors = serie.data.map((_: any, index: number) => {
            const colorIndex = (colorOffset + index) % colors.length;
            return colors[colorIndex];
          });

          return {
            label: serie.name,
            data: serie.data || [],
            backgroundColor: serieColors,
            borderColor: generalBg,
            borderWidth: 2,
            weight: 1,
          };
        });
      } else if (props.data.series && props.data.series.length === 1) {
        chartType = 'pie';
        datasetsConfig = [{
          data: props.data.series[0].data || [],
          backgroundColor: colors.slice(0, props.data.series[0].data.length),
          borderColor: generalBg,
          borderWidth: 2,
        }];
      } else {
        chartType = 'pie';
        datasetsConfig = [{
          data: [],
          backgroundColor: colors,
          borderColor: generalBg,
          borderWidth: 2,
        }];
      }

      new Chart(ctx, {
        type: chartType,
        data: {
          labels: props.data.labels,
          datasets: datasetsConfig,
        },
        options: {
          responsive: true,
          maintainAspectRatio: true,
          plugins: {
            legend: {
              position: 'right',
              labels: {
                color: textColor,
                padding: 15,
                font: { size: 12 },
              },
            },
            tooltip: {
              backgroundColor: style.getPropertyValue('--assistant-bg').trim(),
              titleColor: style.getPropertyValue('--assistant-text').trim(),
              bodyColor: style.getPropertyValue('--general-text').trim(),
              borderColor: style.getPropertyValue('--assistant-accent').trim(),
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