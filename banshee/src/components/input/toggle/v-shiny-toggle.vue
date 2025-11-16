<template>
    <v-switch
        v-model="toggleValue"
        :label="data.label"
        :color="toggleColor"
        :true-value="true"
        :false-value="false"
        hide-details
    >
        <template v-if="data.showText" v-slot:label>
            <span class="mr-2">{{ data.label }}</span>
            <v-chip :color="toggleValue ? 'success' : 'error'" small>
                {{ toggleValue ? data.onText || 'ON' : data.offText || 'OFF' }}
            </v-chip>
        </template>
    </v-switch>
</template>

<script setup lang="ts">
import { ref, watch, computed } from 'vue';
import { ShinyToggle } from '../../models/input/toggle/ShinyToggle';

const props = defineProps<{
    data: ShinyToggle
}>()

const toggleValue = ref(props.data.value);

const toggleColor = computed(() => {
    switch (props.data.toggleType) {
        case 'TOGGLE': return 'primary';
        case 'CHECK': return 'success';
        case 'SWITCH': return 'info';
        case 'LIGHT': return 'warning';
        case 'BATTERY': return 'green';
        case 'STATUS': return 'red';
        case 'THUMBS': return 'blue';
        case 'ARROW': return 'purple';
        case 'STAR': return 'yellow';
        case 'CLASSIC': return 'grey';
        default: return 'primary';
    }
});

watch(() => props.data.value, (newValue) => {
    toggleValue.value = newValue;
});

watch(toggleValue, (newValue) => {
    console.log('Toggle value:', newValue);
    // In a real app, you might emit this value to the parent component
});
</script>
