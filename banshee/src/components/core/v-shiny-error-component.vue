<template>
  <div class="chart-container">
    <div class="error-message">{{ data.text || 'An error occurred' }}</div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { ShinyError } from '../../models/core/error/ShinyError';

export default defineComponent({
  name: 'VShinyErrorComponent',
  props: {
    data: {
      type: Object as () => ShinyError,
      required: true,
    },
  },
});
</script>

<style scoped>
/* All styles are now handled by the global style.css */
</style>