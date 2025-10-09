<template>
  <div class="chart-container">
    <component :is="`h${data.level || 1}`" class="component-title">{{ data.title || 'Default Title' }}</component>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { ShinyTitle } from '../../models/text/title/ShinyTitle';

export default defineComponent({
  name: 'VShinyTitleComponent',
  props: {
    data: {
      type: Object as () => ShinyTitle,
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