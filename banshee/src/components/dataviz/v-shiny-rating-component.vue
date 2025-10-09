<template>
  <div :class="containerClasses" class="chart-container">
    <div class="table-title">{{ data.label || 'Rating' }}</div>
    <div class="shiny-rating-stars" v-html="stars"></div>
    <span class="shiny-rating-value">{{ valueText }}</span>
  </div>
</template>

<script lang="ts">
import { defineComponent, computed } from 'vue';
import { ShinyRating } from '../../models/ShinyRating';
import { ShinyRatingScale, getShinyRatingMaxValue } from '../../models/ShinyRatingScale';

export default defineComponent({
  name: 'VShinyRatingComponent',
  props: {
    data: {
      type: Object as () => ShinyRating,
      required: true,
    },
  },
  setup(props) {
    const style = getComputedStyle(document.documentElement);
    const filledColor = style.getPropertyValue('--yellow-accent').trim();
    const emptyColor = style.getPropertyValue('--general-text').trim();

    const containerClasses = computed(() => {
      let classes = '';
      if (props.data.showBox) {
        classes += ' rating-box';
      }
      return classes;
    });

    const maxValue = computed(() => {
      return props.data.getMaxValue();
    });

    const stars = computed(() => {
      const filledIcon = '★';
      const emptyIcon = '☆';
      const halfIcon = '½';
      let starsHtml = '';
      for (let i = 1; i <= maxValue.value; i++) {
        if (i <= props.data.value) {
          starsHtml += `<span style="color: ${filledColor}">${filledIcon}</span>`;
        } else if (props.data.allowHalfRating && i - 0.5 === props.data.value) {
          starsHtml += `<span style="color: ${filledColor}">${halfIcon}</span>`;
        } else {
          starsHtml += `<span style="color: ${emptyColor}">${emptyIcon}</span>`;
        }
      }
      return starsHtml;
    });

    const valueText = computed(() => {
      if (props.data.showValue) {
        let text = props.data.value;
        if (props.data.showPercentage) {
          text += '%';
        }
        return ` ${props.data.separator || '/'} ${text}`;
      }
      return '';
    });

    return { containerClasses, stars, valueText };
  },
});
</script>

<style scoped>
/* All styles are now handled by the global style.css */
</style>