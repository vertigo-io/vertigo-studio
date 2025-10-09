<template>
  <div class="shiny-paragraph-container">
    <p class="shiny-paragraph">{{ data.text }}</p>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'VShinyParagraphComponent',
  props: {
    data: {
      type: Object,
      required: true,
    },
  },
});
</script>

<style scoped>
.shiny-paragraph-container {
  background-color: #1A202C;
  padding: 15px;
  border-radius: 8px;
  color: #CBD5E0;
}

.shiny-paragraph {
  font-size: 1em;
  line-height: 1.6;
}
</style>