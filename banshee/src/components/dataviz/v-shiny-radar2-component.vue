<template>
  <div class="pa-4" style="width: 100%; height: 500px;">
    <div ref="p5Container" style="width: 100%; height: 100%;"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, watch } from 'vue';
import p5 from 'p5';
import type { ShinyChart } from '@/models/dataviz/chart/ShinyChart';
import type { ShinyChartSerie } from '@/models/dataviz/chart/ShinyChartSerie';

const props = defineProps<{
  model: ShinyChart
}>();

const p5Container = ref<HTMLDivElement | null>(null);
let p5Instance: p5 | null = null;

const sketch = (s: p5) => {
  let chartData: ShinyChart;

  s.setup = () => {
    const container = p5Container.value;
    if (container) {
      s.createCanvas(container.offsetWidth, container.offsetHeight).parent(container);
      s.angleMode(s.RADIANS);
      s.noLoop(); // Only draw when data changes or explicitly requested
    }
  };

  s.draw = () => {
    if (!chartData || !chartData.series || chartData.series.length === 0) {
      s.background(255);
      s.textAlign(s.CENTER, s.CENTER);
      s.textSize(20);
      s.fill(0);
      s.text("No data to display", s.width / 2, s.height / 2);
      return;
    }

    s.background(255);
    s.translate(s.width / 2, s.height / 2);

    const numAxes = chartData.labels.length;
    const angleStep = s.TWO_PI / numAxes;
    const radius = s.min(s.width, s.height) * 0.4;

    // Draw radar grid
    s.stroke(200);
    s.fill(255, 255, 255, 100);
    for (let i = 0; i < 5; i++) { // 5 concentric circles
      s.ellipse(0, 0, (radius / 5) * (i + 1) * 2);
    }
    for (let i = 0; i < numAxes; i++) {
      s.line(0, 0, s.cos(angleStep * i) * radius, s.sin(angleStep * i) * radius);
    }

    // Draw data polygons
    chartData.series.forEach((serie: ShinyChartSerie, serieIndex: number) => {
      s.beginShape();
      const color = s.color(chartData.colors && chartData.colors[serieIndex] ? chartData.colors[serieIndex] : `hsl(${serieIndex * 60}, 70%, 50%)`);
      s.fill(color.levels[0], color.levels[1], color.levels[2], 100); // Semi-transparent fill
      s.stroke(color);
      s.strokeWeight(2);

      serie.data.forEach((value: number, dataIndex: number) => {
        const angle = angleStep * dataIndex;
        const x = s.cos(angle) * (value / 100) * radius; // Assuming max value is 100
        const y = s.sin(angle) * (value / 100) * radius;
        s.vertex(x, y);
      });
      s.endShape(s.CLOSE);
    });

    // Draw labels
    s.fill(0);
    s.noStroke();
    s.textSize(12);
    chartData.labels.forEach((label: string, index: number) => {
      const angle = angleStep * index;
      const x = s.cos(angle) * (radius + 15);
      const y = s.sin(angle) * (radius + 15);
      s.textAlign(s.CENTER, s.CENTER);
      s.text(label, x, y);
    });
  };

  s.updateWithProps = (newChartData: ShinyChart) => {
    chartData = newChartData;
    s.redraw();
  };
};

onMounted(() => {
  if (p5Container.value) {
    p5Instance = new p5(sketch, p5Container.value);
    if (p5Instance && p5Instance.updateWithProps) {
      p5Instance.updateWithProps(props.model);
    }
  }
});

onBeforeUnmount(() => {
  if (p5Instance) {
    p5Instance.remove();
  }
});

watch(() => props.model, (newModel) => {
  if (p5Instance && p5Instance.updateWithProps) {
    p5Instance.updateWithProps(newModel);
  }
}, { deep: true });
</script>
