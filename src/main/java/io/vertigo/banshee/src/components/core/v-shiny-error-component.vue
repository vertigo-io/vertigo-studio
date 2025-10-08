<template>
  <div class="shiny-error-container">
    <div class="shiny-error-message">{{ data.text || 'An error occurred' }}</div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'VShinyErrorComponent',
  props: {
    data: {
      type: Object,
      required: true,
    },
  },
});
</script>

<style scoped>
.shiny-error-container {
  background-color: #FED7D7; /* Red-ish background */
  color: #C53030; /* Darker red text */
  border: 1px solid #FC8181; /* Red border */
  padding: 10px 15px;
  border-radius: 8px;
  margin-bottom: 15px;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  font-size: 0.95em;
  display: flex;
  align-items: center;
}

.shiny-error-message {
  font-weight: bold;
}
</style>