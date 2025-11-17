<template>
    <v-menu
        v-model="menu"
        :close-on-content-click="false"
        transition="scale-transition"
        offset-y
        min-width="auto"
    >
        <template v-slot:activator="{ props }">
            <v-text-field
                v-model="date"
                :label="data.label"
                :required="data.required"
                readonly
                v-bind="props"
            ></v-text-field>
        </template>
        <v-date-picker
            v-model="date"
            @input="menu = false"
        ></v-date-picker>
    </v-menu>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';
import { ShinyDatePicker } from '../../models/input/datepicker/ShinyDatePicker';


const props = defineProps<{
    data: ShinyDatePicker
}>()

const menu = ref(false);
const date = ref(props.data.value);

watch(date, (newDate) => {
    // You might want to emit an event here to update the parent
    console.log(newDate);
});

</script>
