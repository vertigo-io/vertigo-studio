<template>
    <v-autocomplete
        v-model="selected"
        :label="data.label"
        :items="data.options"
        :placeholder="data.placeholder"
        clearable
    ></v-autocomplete>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';
import { ShinyAutocomplete } from '../../models/input/autocomplete/ShinyAutocomplete';

const props = defineProps<{
    data: ShinyAutocomplete
}>()

const selected = ref(props.data.value);

watch(() => props.data.value, (newValue) => {
    selected.value = newValue;
});

watch(selected, (newValue) => {
    console.log('Selected:', newValue);
    // In a real app, you might emit this value to the parent component
});
</script>
