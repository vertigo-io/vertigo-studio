<template>
  <div class="shiny-title-container">
    <component :is="`h${data.level || 1}`" class="shiny-title">{{ data.text }}</component>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'VShinyTitleComponent',
  props: {
    data: {
      type: Object,
      required: true,
    },
  },
});
</script>

<style scoped>
.shiny-title-container {
  background-color: #1A202C;
  padding: 15px;
  border-radius: 8px;
  color: #E2E8F0;
  text-align: center;
}

.shiny-title {
  margin: 0;
  padding: 0;
  font-weight: bold;
}
</style>