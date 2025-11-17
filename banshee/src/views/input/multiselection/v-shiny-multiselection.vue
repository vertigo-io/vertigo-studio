<template>
    <v-select
        v-model="selected"
        :label="data.title"
        :items="data.options"
        multiple
        chips
        clearable
    ></v-select>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';
import { ShinyMultiSelection } from '../../models/input/multiselection/ShinyMultiSelection';

const props = defineProps<{
    data: ShinyMultiSelection
}>()

const selected = ref(props.data.selectedIndices.map(index => props.data.options[index]));

watch(() => props.data.selectedIndices, (newIndices) => {
    selected.value = newIndices.map(index => props.data.options[index]);
});

watch(selected, (newValue) => {
    console.log('Selected:', newValue);
    // In a real app, you might emit this value to the parent component
});
</script>
