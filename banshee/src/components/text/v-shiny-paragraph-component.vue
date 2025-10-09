<template>
  <div class="chart-container">
    <p class="chakra-paragraph">{{ data.text }}</p>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { ShinyParagraph } from '../../models/text/paragraph/ShinyParagraph';

export default defineComponent({
  name: 'VShinyParagraphComponent',
  props: {
    data: {
      type: Object as () => ShinyParagraph,
      required: true,
    },
  },
});
</script>

<style scoped>
/* All styles are now handled by the global style.css */
</style>