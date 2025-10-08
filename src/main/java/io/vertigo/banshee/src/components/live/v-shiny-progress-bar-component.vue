<template>
  <div class="shiny-progress-container" :id="data.id">
    <div class="shiny-progress-bar" :style="{ width: percentage + '%' }"></div>
    <div class="shiny-progress-text">{{ Math.round(percentage) }}%</div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'VShinyProgressBarComponent',
  props: {
    data: {
      type: Object,
      required: true,
    },
  },
  computed: {
    percentage(): number {
      return Math.min((this.data.value / this.data.total) * 100, 100);
    },
  },
});
</script>

<style scoped>
.shiny-progress-container {
  width: 100%;
  background-color: #4A5568; /* Gray background */
  border-radius: 5px;
  overflow: hidden;
  position: relative;
  height: 25px; /* Height of the progress bar */
  margin-bottom: 15px;
}

.shiny-progress-bar {
  height: 100%;
  background-color: #3182CE; /* Blue progress */
  width: 0%; /* Initial width */
  border-radius: 5px;
  text-align: center;
  line-height: 25px; /* Center text vertically */
  color: white;
  transition: width 0.5s ease-in-out;
}

.shiny-progress-text {
  position: absolute;
  width: 100%;
  text-align: center;
  line-height: 25px;
  color: white;
  font-weight: bold;
  top: 0;
  left: 0;
}
</style>