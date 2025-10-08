<template>
  <div :class="containerClasses" class="shiny-rating-container">
    <span class="shiny-rating-label">{{ data.label }}</span>
    <div class="shiny-rating-stars" v-html="stars"></div>
    <span class="shiny-rating-value">{{ valueText }}</span>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'VShinyRatingComponent',
  props: {
    data: {
      type: Object,
      required: true,
    },
  },
  computed: {
    containerClasses(): string {
      let classes = 'rating-container';
      if (this.data.showBox) {
        classes += ' rating-box';
      }
      return classes;
    },
    maxValue(): number {
      if (this.data.customMaxValue !== -1) {
        return this.data.customMaxValue;
      }
      switch (this.data.scale) {
        case 'SCALE_10':
          return 10;
        case 'SCALE_100':
          return 100;
        default:
          return 5;
      }
    },
    stars(): string {
      const filledColor = 'gold';
      const emptyColor = 'lightgray';
      const filledIcon = '★';
      const emptyIcon = '☆';
      const halfIcon = '½';
      let starsHtml = '';
      for (let i = 1; i <= this.maxValue; i++) {
        if (i <= this.data.value) {
          starsHtml += `<span style="color: ${filledColor}">${filledIcon}</span>`;
        } else if (this.data.allowHalfRating && i - 0.5 === this.data.value) {
          starsHtml += `<span style="color: ${filledColor}">${halfIcon}</span>`;
        } else {
          starsHtml += `<span style="color: ${emptyColor}">${emptyIcon}</span>`;
        }
      }
      return starsHtml;
    },
    valueText(): string {
      if (this.data.showValue) {
        let text = this.data.value;
        if (this.data.showPercentage) {
          text += '%';
        }
        return ` ${this.data.separator || '/'} ${text}`;
      }
      return '';
    },
  },
});
</script>

<style scoped>
.shiny-rating-container {
  display: flex;
  align-items: center;
  background-color: #1A202C;
  padding: 15px;
  border-radius: 8px;
  color: #CBD5E0;
}

.shiny-rating-container.rating-box {
  border: 1px solid #4A5568;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.shiny-rating-label {
  margin-right: 10px;
  font-weight: bold;
  color: #E2E8F0;
}

.shiny-rating-stars span {
  font-size: 1.5em;
  line-height: 1;
}

.shiny-rating-value {
  margin-left: 10px;
  font-weight: bold;
  color: #90CDF4;
}
</style>