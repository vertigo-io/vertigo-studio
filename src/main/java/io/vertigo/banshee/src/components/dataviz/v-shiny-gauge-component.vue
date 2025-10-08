<template>
  <div class="shiny-gauge-container">
    <h3 class="shiny-component-title">{{ data.title || 'Shiny Gauge' }}</h3>
    <canvas :id="canvasId" class="shiny-gauge-canvas"></canvas>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
// Assuming 'Gauge' is an external library, you might need to import it like:
// import { Gauge } from 'gauge-js'; // Adjust path as necessary

declare const Gauge: any; // Declare Gauge to avoid TypeScript errors if not imported

export default defineComponent({
  name: 'VShinyGaugeComponent',
  props: {
    data: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      canvasId: `gauge-${Math.random().toString(36).substr(2, 9)}`,
    };
  },
  mounted() {
    const target = document.getElementById(this.canvasId) as HTMLCanvasElement | null;
    if (!target) {
      console.error(`Canvas element not found for ID: ${this.canvasId}`);
      return;
    }
    const gauge = new Gauge(target);
    gauge.maxValue = this.data.maxValue || 100;
    gauge.setMinValue(this.data.min || 0);
    gauge.set(this.data.value || 0);
  },
});
</script>

<style scoped>
.shiny-gauge-container {
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

.shiny-gauge-canvas {
  width: 100%;
  max-width: 200px; /* Adjust as needed */
  height: auto;
}
</style>