<template>
  <div  class="pa-4">
    <v-slider
      v-model="sliderValue"
      :label="data.label"
      :min="data.min"
      :max="data.max"
      :step="data.step"
      :color="data.color || 'primary'"
      :thumb-label="data.thumbLabel ? 'always' : undefined"
      class="align-center"
      hide-details
    >
      <template v-slot:append>
        <v-text-field
          v-model="sliderValue"
          type="number"
          style="width: 80px"
          density="compact"
          hide-details
          variant="outlined"
        ></v-text-field>
      </template>
    </v-slider>
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';
import type { ShinySlider } from '@/models/input/slider/ShinySlider';

const props = defineProps<{
  data: ShinySlider
}>()

const initialValue = props.data ? (props.data.value ?? 0) : 0;
const sliderValue = ref(initialValue);

// Watch for changes in the model prop and update the local sliderValue
watch(() => props.data?.value, (newValue) => {
  sliderValue.value = newValue ?? 0;
});

// Note: In a real application, you might want to emit an event
// when sliderValue changes due to user interaction.
// For now, this component just displays the slider.

</script>
