<template>
    <v-text-field
        v-model="textValue"
        :label="data.label"
        :required="data.required"
        :rules="rules"
        :placeholder="data.placeholder"
        :items="data.suggestions"
        clearable
    ></v-text-field>
</template>

<script setup lang="ts">
import { ref, watch, computed } from 'vue';
import { ShinyInputText } from '../../models/input/text/ShinyInputText';

const props = defineProps<{
    data: ShinyInputText
}>()

const textValue = ref(props.data.value || props.data.defaultValue);

const rules = computed(() => {
    const validationRules: ((value: string) => boolean | string)[] = [];

    if (props.data.required) {
        validationRules.push(v => !!v || 'This field is required');
    }

    if (props.data.validationPattern) {
        const regex = new RegExp(props.data.validationPattern);
        validationRules.push(v => regex.test(v) || 'Invalid format');
    }

    return validationRules;
});

watch(() => props.data.value, (newValue) => {
    textValue.value = newValue;
});

watch(textValue, (newValue) => {
    console.log('Text value:', newValue);
    // In a real app, you might emit this value to the parent component
});
</script>
