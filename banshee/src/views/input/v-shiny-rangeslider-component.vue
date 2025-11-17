<template>
    <v-range-slider
        v-model="range"
        :label="data.label"
        :min="data.min"
        :max="data.max"
        :step="data.step"
        :color="data.color || 'primary'"
        :thumb-label="data.thumbLabel ? 'always' : undefined"
        hide-details
    >
        <template v-slot:append>
            <v-text-field
                :model-value="range[1]"
                type="number"
                style="width: 80px"
                density="compact"
                hide-details
                variant="outlined"
                @update:model-value="setRangeEnd"
            ></v-text-field>
        </template>
    </v-range-slider>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';
import { ShinyRangeSlider } from '../../models/input/rangeslider/ShinyRangeSlider';

const props = defineProps<{
    data: ShinyRangeSlider
}>()

const range = ref(props.data.value);

watch(() => props.data.value, (newValue) => {
    range.value = newValue;
});

const setRangeEnd = (value: string) => {
    const numValue = Number(value);
    if (!isNaN(numValue)) {
        range.value = [range.value[0], numValue];
    }
};

watch(range, (newValue) => {
    console.log('Range selected:', newValue);
    // In a real app, you might emit this value to the parent component
});
</script>
