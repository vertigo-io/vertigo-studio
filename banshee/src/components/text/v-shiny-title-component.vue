<template>
  <div class="chart-container">
    <component :is="`h${data.level || 1}`" class="component-title">{{ data.text || 'Default Title' }}</component>
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
.component-title {
  color: var(--assistant-text);
  margin: 0;
  padding: 0;
}
</style>