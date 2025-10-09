<template>
  <div class="chart-container">
    <div class="table-title">{{ data.title || 'Sankey Diagram' }}</div>
    <canvas :id="canvasId" class="sankey-canvas"></canvas>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted } from 'vue';
import { ShinySankey } from '../../models/dataviz/sankey/ShinySankey';

declare const Chart: any;

export default defineComponent({
  name: 'VShinySankeyComponent',
  props: {
    data: {
      type: Object as () => ShinySankey,
      required: true,
    },
  },
  setup(props) {
    const canvasId = `sankey-${Math.random().toString(36).substr(2, 9)}`;

    onMounted(() => {
      const style = getComputedStyle(document.documentElement);
      const textColor = style.getPropertyValue('--chakra-paragraph-text').trim();
      const assistantBg = style.getPropertyValue('--assistant-bg').trim();
      const assistantText = style.getPropertyValue('--assistant-text').trim();
      const generalText = style.getPropertyValue('--general-text').trim();
      const assistantAccent = style.getPropertyValue('--assistant-accent').trim();

      const target = document.getElementById(canvasId) as HTMLCanvasElement | null;
      if (!target) return;

      new Chart(target, {
        type: 'sankey',
        data: {
          datasets: [{
            data: props.data.data,
            colorFrom: (c: any) => style.getPropertyValue('--json-key').trim(), // Example color
            colorTo: (c: any) => style.getPropertyValue('--json-boolean').trim(), // Example color
            colorMode: 'gradient',
          }],
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            title: {
              display: true,
              text: props.data.title,
              color: assistantText,
            },
            legend: { display: false },
            tooltip: {
              backgroundColor: assistantBg,
              titleColor: assistantText,
              bodyColor: generalText,
              borderColor: assistantAccent,
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
    });

    return { canvasId };
  },
});
</script>

<style scoped>
/* All styles are now handled by the global style.css */
</style>